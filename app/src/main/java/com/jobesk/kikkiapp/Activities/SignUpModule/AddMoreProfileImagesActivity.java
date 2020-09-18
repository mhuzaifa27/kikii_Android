package com.jobesk.kikkiapp.Activities.SignUpModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jobesk.kikkiapp.Netwrok.Constant;
import com.jobesk.kikkiapp.R;
import com.jobesk.kikkiapp.Utils.CustomLoader;
import com.jobesk.kikkiapp.Utils.FileUploader;
import com.jobesk.kikkiapp.Utils.SelectImage;
import com.jobesk.kikkiapp.Utils.SessionManager;
import com.jobesk.kikkiapp.Utils.ShowSelectImageBottomSheet;
import com.joooonho.SelectableRoundedImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;

import static com.jobesk.kikkiapp.Utils.ShowSelectImageBottomSheet.*;

public class AddMoreProfileImagesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddMoreProfileImagesAct";
    private Context mContext = AddMoreProfileImagesActivity.this;
    private Activity activity = AddMoreProfileImagesActivity.this;

    private Button btn_next;
    private static final int TAKE_PICTURE_FROM_CAMERA_FOR_PROFILE = 100;
    private static final int TAKE_PICTURE_FROM_GALLERY_FOR_PROFILE = 200;

    private boolean isSelected;
    private SessionManager sessionManager;
    private Bitmap bitmap,bmp;
    private String currentPhotoPath;
    private CustomLoader customLoader;

    private SelectableRoundedImageView img_user, img_selected_1, img_selected_2, img_selected_3, img_selected_4, img_selected_5, img_selected_6, img_selected_7, img_selected_8;
    private ImageView img_select_1, img_select_2, img_select_3, img_select_4, img_select_5, img_select_6, img_select_7, img_select_8;
    private static int setImageOn = 0;
    private MultipartBody.Part[] imageParts = new MultipartBody.Part[10];
    private ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    ArrayList<String> files = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more_profile_images);

        initComponents();
        getIntentData();

        btn_next.setOnClickListener(this);

        img_select_1.setOnClickListener(this);
        img_select_2.setOnClickListener(this);
        img_select_3.setOnClickListener(this);
        img_select_4.setOnClickListener(this);
        img_select_5.setOnClickListener(this);
        img_select_6.setOnClickListener(this);
        img_select_7.setOnClickListener(this);
        img_select_8.setOnClickListener(this);

    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("bitmap");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        img_user.setImageBitmap(bmp);
    }

    private void initComponents() {
        customLoader = new CustomLoader(this, false);
        sessionManager = new SessionManager(this);

        btn_next = findViewById(R.id.btn_next);

        img_user = findViewById(R.id.img_user);
        img_selected_1 = findViewById(R.id.img_selected_1);
        img_selected_2 = findViewById(R.id.img_selected_2);
        img_selected_3 = findViewById(R.id.img_selected_3);
        img_selected_4 = findViewById(R.id.img_selected_4);
        img_selected_5 = findViewById(R.id.img_selected_5);
        img_selected_6 = findViewById(R.id.img_selected_6);
        img_selected_7 = findViewById(R.id.img_selected_7);
        img_selected_8 = findViewById(R.id.img_selected_8);

        img_select_1 = findViewById(R.id.img_select_1);
        img_select_2 = findViewById(R.id.img_select_2);
        img_select_3 = findViewById(R.id.img_select_3);
        img_select_4 = findViewById(R.id.img_select_4);
        img_select_5 = findViewById(R.id.img_select_5);
        img_select_6 = findViewById(R.id.img_select_6);
        img_select_7 = findViewById(R.id.img_select_7);
        img_select_8 = findViewById(R.id.img_select_8);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                callUploadData();
                break;
            case R.id.img_select_1:
                setImageOn = 1;
                ShowSelectImageBottomSheet.showDialog(activity, getWindow().getDecorView().getRootView(), Constant.MULTIPLE);
                break;
            case R.id.img_select_2:
                setImageOn = 2;
                ShowSelectImageBottomSheet.showDialog(activity, getWindow().getDecorView().getRootView(), Constant.MULTIPLE);
                break;
            case R.id.img_select_3:
                setImageOn = 3;
                ShowSelectImageBottomSheet.showDialog(activity, getWindow().getDecorView().getRootView(), Constant.MULTIPLE);
                break;
            case R.id.img_select_4:
                setImageOn = 4;
                ShowSelectImageBottomSheet.showDialog(activity, getWindow().getDecorView().getRootView(), Constant.MULTIPLE);
                break;
            case R.id.img_select_5:
                setImageOn = 5;
                ShowSelectImageBottomSheet.showDialog(activity, getWindow().getDecorView().getRootView(), Constant.MULTIPLE);
                break;
            case R.id.img_select_6:
                setImageOn = 6;
                ShowSelectImageBottomSheet.showDialog(activity, getWindow().getDecorView().getRootView(), Constant.MULTIPLE);
                break;
            case R.id.img_select_7:
                setImageOn = 7;
                ShowSelectImageBottomSheet.showDialog(activity, getWindow().getDecorView().getRootView(), Constant.MULTIPLE);
                break;
            case R.id.img_select_8:
                setImageOn = 8;
                ShowSelectImageBottomSheet.showDialog(activity, getWindow().getDecorView().getRootView(), Constant.MULTIPLE);
                break;
        }
    }

    private void callUploadData() {
        for (int i = 0; i < mArrayUri.size(); i++) {
            getImageFilePath(mArrayUri.get(i));
        }
        if (files.size() > 0) {
            uploadFiles();
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
                currentPhotoPath = SelectImage.compressImage(uri, activity);
                Log.d(TAG, "onActivityResult: " + currentPhotoPath);
                /*MultipartBody.Part body = prepareFilePart(Constant.PROFILE_PIC);
                RequestBody token = RequestBody.create(MediaType.parse("authorization"), sessionManager.getAccessToken());
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("token", token);*/
                customLoader.hideIndicator();
                setBitmap(bitmap);
                //goToNextActivity(bitmap);
                //updateProfilePhoto(map, body);
            } else {
                customLoader.hideIndicator();
                isSelected = false;
                Toast.makeText(activity, "Image not captured", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == TAKE_PICTURE_FROM_GALLERY_FOR_PROFILE && resultCode == Activity.RESULT_OK) {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                if (mClipData.getItemCount() <= 8) {
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                            setBitmap(bitmap, i + 1);
                            currentPhotoPath = SelectImage.compressImage(uri, activity);
                            Log.d(TAG, "onActivityResult: " + currentPhotoPath);
                            MultipartBody.Part body = SelectImage.prepareFilePart(Constant.PROFILE_PIC, currentPhotoPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(mContext, "You cannot select more than 8 pictures", Toast.LENGTH_SHORT).show();
                }
                Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
            } else if (data.getData() != null) {
                Uri mImageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), mImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setBitmap(bitmap);
            }

            customLoader.hideIndicator();

            /*isSelected = true;
            bitmap = null;
            Uri pictureUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), pictureUri);
                isSelected = true;
                *//*compressImage(pictureUri);
                MultipartBody.Part body = prepareFilePart(Constant.PROFILE_PIC);
                RequestBody token = RequestBody.create(
                        MediaType.parse("authorization"), sessionManager.getAccessToken());
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("authorization", token);*//*
                customLoader.hideIndicator();
                setBitmap(bitmap);
                //goToNextActivity(bitmap);
                //updateProfilePhoto(map, body);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }*/
        } else {
            customLoader.hideIndicator();
        }
    }

    private void setBitmap(Bitmap bitmap) {
        switch (setImageOn) {
            case 1:
                img_selected_1.setImageBitmap(bitmap);
                img_select_1.setVisibility(View.GONE);
                break;
            case 2:
                img_selected_2.setImageBitmap(bitmap);
                img_select_2.setVisibility(View.GONE);

                break;
            case 3:
                img_selected_3.setImageBitmap(bitmap);
                img_select_3.setVisibility(View.GONE);

                break;
            case 4:
                img_selected_4.setImageBitmap(bitmap);
                img_select_4.setVisibility(View.GONE);
                break;
            case 5:
                img_selected_5.setImageBitmap(bitmap);
                img_select_5.setVisibility(View.GONE);

                break;
            case 6:
                img_selected_6.setImageBitmap(bitmap);
                img_select_6.setVisibility(View.GONE);
                break;
            case 7:
                img_selected_7.setImageBitmap(bitmap);
                img_select_7.setVisibility(View.GONE);
                break;
            case 8:
                img_selected_8.setImageBitmap(bitmap);
                img_select_8.setVisibility(View.GONE);
                break;
        }
    }

    private void setBitmap(Bitmap bitmap, int setImageOn) {
        switch (setImageOn) {
            case 1:
                img_selected_1.setImageBitmap(bitmap);
                img_select_1.setVisibility(View.GONE);
                break;
            case 2:
                img_selected_2.setImageBitmap(bitmap);
                img_select_2.setVisibility(View.GONE);

                break;
            case 3:
                img_selected_3.setImageBitmap(bitmap);
                img_select_3.setVisibility(View.GONE);

                break;
            case 4:
                img_selected_4.setImageBitmap(bitmap);
                img_select_4.setVisibility(View.GONE);
                break;
            case 5:
                img_selected_5.setImageBitmap(bitmap);
                img_select_5.setVisibility(View.GONE);

                break;
            case 6:
                img_selected_6.setImageBitmap(bitmap);
                img_select_6.setVisibility(View.GONE);
                break;
            case 7:
                img_selected_7.setImageBitmap(bitmap);
                img_select_7.setVisibility(View.GONE);
                break;
            case 8:
                img_selected_8.setImageBitmap(bitmap);
                img_select_8.setVisibility(View.GONE);
                break;
        }
    }

    public void getImageFilePath(Uri uri) {
        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            files.add(imagePath);
            cursor.close();
        }
    }

    public void uploadFiles() {
        File[] filesToUpload = new File[files.size()];
        for (int i = 0; i < files.size(); i++) {
            filesToUpload[i] = new File(files.get(i));
        }
        customLoader.showIndicator();
        //showProgress("Uploading media ...");
        FileUploader fileUploader = new FileUploader(activity);
        fileUploader.uploadFiles("new_pics[]", filesToUpload, new FileUploader.FileUploaderCallback() {
            @Override
            public void onError() {
                customLoader.hideIndicator();
                Log.e("RESPONSE ","error");
            }

            @Override
            public void onFinish(String[] responses) {
                customLoader.setProgressVisibility(false);
                customLoader.hideIndicator();
                startActivity(new Intent(mContext,AgreementActivity.class));
                //hideProgress();
                for (int i = 0; i < responses.length; i++) {
                    String str = responses[i];
                    Log.e("RESPONSE " + i, responses[i]);
                }
            }

            @Override
            public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {
                customLoader.setProgressVisibility(true);
                customLoader.setProgress("Uploading \n"+totalpercent+"%");
                Log.e("Progress Status", currentpercent + " " + totalpercent + " " + filenumber);
            }
        },sessionManager.getAccessToken());
    }

   /* public void updateProgress(int val, String title, String msg){
        pDialog.setTitle(title);
        pDialog.setMessage(msg);
        pDialog.setProgress(val);
    }

    public void showProgress(String str){
        try{
            pDialog.setCancelable(false);
            pDialog.setTitle("Please wait");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setMax(100); // Progress Dialog Max Value
            pDialog.setMessage(str);
            if (pDialog.isShowing())
                pDialog.dismiss();
            pDialog.show();
        }catch (Exception e){

        }
    }

    public void hideProgress(){
        try{
            if (pDialog.isShowing())
                pDialog.dismiss();
        }catch (Exception e){

        }
    }*/

}
