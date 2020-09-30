package com.example.kikkiapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.SignUpModule.AgreementActivity;
import com.example.kikkiapp.Adapters.PostMediaAdapter;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.FileUploader;
import com.example.kikkiapp.Utils.SelectImage;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.example.kikkiapp.Utils.ShowSelectImageBottomSheet;

import net.alhazmy13.mediapicker.Image.ImagePicker;
import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static com.example.kikkiapp.Utils.CommonMethods.goBack;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CreatePostActivity";
    private Context context=CreatePostActivity.this;
    private EditText et_body;
    private TextView tv_name;
    private ImageView img_select_image,img_back,img_ok;
    private CircleImageView img_user;
    private Call<CallbackStatus> callbackStatusCall;
    private CallbackStatus responseResendCode;
    private CustomLoader customLoader;
    private SessionManager sessionManager;
    private Map<String,String> createPostParams=new HashMap<>();
    private Map<String, RequestBody> createPostMultipartParams = new HashMap<>();

    private String body;
    private RecyclerView rv_media;
    private PostMediaAdapter postMediaAdapter;
    private List<String> mediaPaths;
    private List<MultipartBody.Part> imagesList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        initComponents();
        setUserData();

        img_back.setOnClickListener(this);
        img_ok.setOnClickListener(this);
        img_select_image.setOnClickListener(this);
    }

    private void setUserData() {
        img_ok.setVisibility(View.VISIBLE);
        tv_name.setText(sessionManager.getUserName());
        Glide
                .with(context)
                .load(sessionManager.getPhoto())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(img_user);
    }

    private void initComponents() {
        sessionManager=new SessionManager(this);
        customLoader=new CustomLoader(this,false);

        et_body=findViewById(R.id.et_body);

        rv_media=findViewById(R.id.rv_media);

        tv_name=findViewById(R.id.tv_name);

        img_user=findViewById(R.id.img_user);
        img_select_image=findViewById(R.id.img_select_image);
        img_back=findViewById(R.id.img_back);
        img_ok=findViewById(R.id.img_ok);
        img_select_image=findViewById(R.id.img_select_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                goBack(this);
                break;
            case R.id.img_ok:
                if(et_body.getText().toString().isEmpty()){
                    et_body.setError(getResources().getString(R.string.et_error));
                } else{
                    body=et_body.getText().toString();
                    createPostParams.put(Constant.BODY,body);
                    if(mediaPaths.size()>0){
                        RequestBody rBody = RequestBody.create(
                                MediaType.parse(Constant.BODY), body);
                        createPostMultipartParams.put(Constant.BODY, rBody);
                        uploadFiles();
                    }
                    else{
                        createPost();
                    }
                }
                break;
            case R.id.img_select_image:
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this,img_select_image);
                break;
        }
    }

    public void uploadFiles() {
        for (int i = 0; i <mediaPaths.size() ; i++) {
            imagesList.add(SelectImage.prepareFilePart("media[]",mediaPaths.get(i)));
        }
        createPostWithImages();
        /*File[] filesToUpload = new File[mediaPaths.size()];
        for (int i = 0; i < mediaPaths.size(); i++) {
            filesToUpload[i] = new File(mediaPaths.get(i));
        }
        customLoader.showIndicator();*/

        /*//showProgress("Uploading media ...");
        FileUploader fileUploader = new FileUploader(this);
        fileUploader.uploadFiles("media[]", filesToUpload, new FileUploader.FileUploaderCallback() {
            @Override
            public void onError() {
                customLoader.hideIndicator();
                Log.e("RESPONSE ","error");
            }

            @Override
            public void onFinish(String[] responses) {
                customLoader.setProgressVisibility(false);
                customLoader.hideIndicator();
                onBackPressed();
            }

            @Override
            public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {
                customLoader.setProgressVisibility(true);
                customLoader.setProgress("Uploading \n"+totalpercent+"%");
                Log.e("Progress Status", currentpercent + " " + totalpercent + " " + filenumber);
            }
        },sessionManager.getAccessToken());*/
    }

    private void createPost() {
            customLoader.showIndicator();
            API api = RestAdapter.createAPI(context);
            Log.d(TAG, "createPost: " + sessionManager.getAccessToken());
            callbackStatusCall = api.createPost(sessionManager.getAccessToken(),createPostParams);
            callbackStatusCall.enqueue(new Callback<CallbackStatus>() {
                @Override
                public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                    Log.d(TAG, "onResponse: " + response);
                    responseResendCode = response.body();
                    if (responseResendCode != null) {
                        customLoader.hideIndicator();
                        if (responseResendCode.getSuccess()) {
                            Log.d(TAG, "onResponse: " + responseResendCode.getMessage());
                            Toast.makeText(context, responseResendCode.getMessage(), Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Log.d(TAG, "onResponse: " + responseResendCode.getMessage());
                            Toast.makeText(context, responseResendCode.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                    }
                }

                @Override
                public void onFailure(Call<CallbackStatus> call, Throwable t) {
                    if (!call.isCanceled()) {
                        Log.d(TAG, "onResponse: " + t.getMessage());
                        customLoader.hideIndicator();
                    }
                }
            });
    }
    private void createPostWithImages() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "createPost: " + sessionManager.getAccessToken());
        callbackStatusCall = api.createPostWithMedia(sessionManager.getAccessToken(),createPostMultipartParams,imagesList);
        callbackStatusCall.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                responseResendCode = response.body();
                if (responseResendCode != null) {
                    customLoader.hideIndicator();
                    if (responseResendCode.getSuccess()) {
                        Log.d(TAG, "onResponse: " + responseResendCode.getMessage());
                        Toast.makeText(context, responseResendCode.getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Log.d(TAG, "onResponse: " + responseResendCode.getMessage());
                        Toast.makeText(context, responseResendCode.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackStatus> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaPaths =  data.getStringArrayListExtra(VideoPicker.EXTRA_VIDEO_PATH);
            Log.d("hhhh", "onActivityResult: "+ mediaPaths.size());
            rv_media.setLayoutManager(new GridLayoutManager(context,3));
            postMediaAdapter=new PostMediaAdapter(mediaPaths,context,"video");
            rv_media.setAdapter(postMediaAdapter);
            postMediaAdapter.setOnClickListeners(new PostMediaAdapter.IClicks() {
                @Override
                public void onCancelClick(View view, String path, int position) {
                    postMediaAdapter.remove(path);
                }
            });
        }
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            Log.d("hhhh", "onActivityResult: "+ mediaPaths.size());
            rv_media.setLayoutManager(new GridLayoutManager(context,3));
            postMediaAdapter=new PostMediaAdapter(mediaPaths,context,"image");
            rv_media.setAdapter(postMediaAdapter);
            postMediaAdapter.setOnClickListeners(new PostMediaAdapter.IClicks() {
                @Override
                public void onCancelClick(View view, String path, int position) {
                    postMediaAdapter.remove(path);
                }
            });
        }
    }
}
