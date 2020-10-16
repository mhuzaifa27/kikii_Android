package com.example.kikkiapp.Activities.SignUpModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kikkiapp.Callbacks.CallbackUpdateProfile;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CommonMethods;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SelectImage;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.example.kikkiapp.Utils.ShowSelectImageBottomSheet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProfileImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddProfileImageActivity";
    private Activity activity =AddProfileImageActivity.this;

    private static final int TAKE_PICTURE_FROM_CAMERA_FOR_PROFILE = 100;
    private static final int TAKE_PICTURE_FROM_GALLERY_FOR_PROFILE = 200;

    private Button btn_add_image;
    private boolean isSelected;
    private SessionManager sessionManager;
    private Bitmap bitmap;
    private String currentPhotoPath;
    private CustomLoader customLoader;

    private Call<CallbackUpdateProfile> callbackStatusCall;
    private CallbackUpdateProfile responseUpdatePhoto;
    private ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_image);

        initComponents();

        btn_add_image.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    private void initComponents() {
        btn_add_image=findViewById(R.id.btn_add_image);

        sessionManager=new SessionManager(this);

        customLoader=new CustomLoader(this,false);

        img_back=findViewById(R.id.img_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_image:
                ShowSelectImageBottomSheet.showDialog(activity,getWindow().getDecorView().getRootView(),Constant.SINGLE);
                break;
            case R.id.img_back:
                CommonMethods.goBack(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        customLoader.showIndicator();
        /**PROFILE PICTURE FROM CAMERA**/
        if (requestCode == TAKE_PICTURE_FROM_CAMERA_FOR_PROFILE && resultCode == Activity.RESULT_OK) {
            isSelected = true;
            bitmap = (Bitmap) data.getExtras().get("data");
            Uri uri = SelectImage.getImageUri(activity, bitmap);
            if (uri != null) {
                currentPhotoPath=SelectImage.compressImage(uri,activity);
                Log.d(TAG, "onActivityResult: " + currentPhotoPath);
                MultipartBody.Part body = SelectImage.prepareFilePart(Constant.PROFILE_PIC,currentPhotoPath);
                RequestBody token = RequestBody.create(MediaType.parse("Authorization"), sessionManager.getAccessToken());
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("Authorization", token);
                updateProfilePhoto(body);
            } else {
                customLoader.hideIndicator();
                isSelected = false;
                Toast.makeText(activity, "Image not captured", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == TAKE_PICTURE_FROM_GALLERY_FOR_PROFILE && resultCode == Activity.RESULT_OK) {
            isSelected = true;
            bitmap = null;
            Uri pictureUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), pictureUri);
                isSelected = true;
                currentPhotoPath=SelectImage.compressImage(pictureUri,activity);
                Log.d(TAG, "onActivityResult: " + currentPhotoPath);
                MultipartBody.Part body = prepareFilePart(Constant.PROFILE_PIC,currentPhotoPath);
                RequestBody token = RequestBody.create(MediaType.parse("Authorization"), sessionManager.getAccessToken());
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("Authorization", token);
                updateProfilePhoto(body);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else{
            customLoader.hideIndicator();
        }
    }
    public MultipartBody.Part prepareFilePart(String partName,String currentPhotoPath) {
        File file = new File(currentPhotoPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse(partName), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
    private void updateProfilePhoto(MultipartBody.Part body) {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(activity);
        Log.d(TAG, "updateProfilePhoto: "+sessionManager.getAccessToken());
        callbackStatusCall = api.updateProfilePhoto(sessionManager.getAccessToken(), body);
        callbackStatusCall.enqueue(new Callback<CallbackUpdateProfile>() {
            @Override
            public void onResponse(Call<CallbackUpdateProfile> call, Response<CallbackUpdateProfile> response) {
                Log.d("ressss", "onResponse: " + response);
                responseUpdatePhoto = response.body();
                if (responseUpdatePhoto != null) {
                    if (responseUpdatePhoto.getSuccess()) {
                        Toast.makeText(activity, responseUpdatePhoto.getMessage(), Toast.LENGTH_SHORT).show();
                        customLoader.hideIndicator();
                        goToNextActivity(bitmap);
                    } else {
                        Toast.makeText(activity, responseUpdatePhoto.getMessage(), Toast.LENGTH_SHORT).show();
                        customLoader.hideIndicator();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(activity);
                }
            }

            @Override
            public void onFailure(Call<CallbackUpdateProfile> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d("ressss", "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void goToNextActivity(Bitmap bitmap){
        byte[] byteArray = SelectImage.getByteArray(bitmap);
        Intent intent=new Intent(activity,VerifyProfileImagesActivity.class);
        intent.putExtra("bitmap",byteArray);
        Log.d(TAG, "onActivityResult: "+byteArray);
        startActivity(intent);
    }
}
