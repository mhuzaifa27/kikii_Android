package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CommonMethods;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        initComponents();
        setUserData();

        img_back.setOnClickListener(this);
        img_ok.setOnClickListener(this);
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

        tv_name=findViewById(R.id.tv_name);

        img_user=findViewById(R.id.img_user);
        img_select_image=findViewById(R.id.img_select_image);
        img_back=findViewById(R.id.img_back);
        img_ok=findViewById(R.id.img_ok);
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
                }
                else{
                    body=et_body.getText().toString();
                    createPostParams.put(Constant.BODY,body);
                    createPost();
                }
                break;
        }
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
}
