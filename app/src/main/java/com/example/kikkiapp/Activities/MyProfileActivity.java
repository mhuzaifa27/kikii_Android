package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Callbacks.CallbackGetProfile;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.example.kikkiapp.Utils.UtilityFunctions;
import com.joooonho.SelectableRoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyProfileActivity";
    private Context context = MyProfileActivity.this;
    private Activity activity = MyProfileActivity.this;

    private SelectableRoundedImageView img_user;
    private TextView tv_user_name, tv_user_age, tv_gender, tv_pronouns, tv_distance, tv_bio, tv_friends_count;
    private RecyclerView rv_curiosities;
    private Button btn_view_friends;
    private ImageView img_edit;
    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetProfile> callbackGetProfileCall;
    private CallbackGetProfile responseGetProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        initComponents();
        getUserProfile();

        img_edit.setOnClickListener(this);
        btn_view_friends.setOnClickListener(this);
    }

    private void getUserProfile() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetProfileCall = api.getProfile(sessionManager.getAccessToken(), String.valueOf(0));
        callbackGetProfileCall.enqueue(new Callback<CallbackGetProfile>() {
            @Override
            public void onResponse(Call<CallbackGetProfile> call, Response<CallbackGetProfile> response) {
                Log.d(TAG, "onResponse: " + response);
                responseGetProfile = response.body();
                if (responseGetProfile != null) {
                    if (responseGetProfile.getSuccess()) {
                        customLoader.hideIndicator();
                        //Toast.makeText(context, responseGetProfile.getMessage(), Toast.LENGTH_SHORT).show();
                        setData();
                    } else {
                        Log.d(TAG, "onResponse: " + responseGetProfile.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseGetProfile.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetProfile> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setData() {
        tv_user_name.setText(responseGetProfile.getUser().getName());
        tv_friends_count.setText(String.valueOf(responseGetProfile.getUser().getFriends_count()));
        if (responseGetProfile.getUser().getPronouns() != null)
            tv_pronouns.setText(responseGetProfile.getUser().getPronouns());
        else
            tv_pronouns.setText(Constant.NOT_SET);
        if (responseGetProfile.getUser().getGenderIdentity() != null)
            tv_gender.setText(responseGetProfile.getUser().getGenderIdentity());
        else
            tv_gender.setText(Constant.NOT_SET);
        /*if (responseGetProfile.getUser().getPronouns() != null)
            tv_distance.setText(responseGetProfile.getUser().getPronouns());
        else
            tv_distance.setText(Constant.NOT_SET);*/
        if (responseGetProfile.getUser().getPronouns() != null)
            tv_bio.setText(responseGetProfile.getUser().getBio());
        else
            tv_bio.setText(Constant.NOT_SET);
        Glide
                .with(context)
                .load(responseGetProfile.getUser().getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(img_user);
        String birthdateStr = responseGetProfile.getUser().getBirthday();
        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        Date birthdate = null;
        try {
            birthdate = df.parse(birthdateStr);
            tv_user_age.setText(String.valueOf(UtilityFunctions.calculateAge(birthdate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        customLoader = new CustomLoader(this, false);
        sessionManager = new SessionManager(this);

        img_user = findViewById(R.id.img_user);
        img_edit = findViewById(R.id.img_edit);

        tv_bio = findViewById(R.id.tv_bio);
        tv_distance = findViewById(R.id.tv_distance);
        tv_gender = findViewById(R.id.tv_gender);
        tv_pronouns = findViewById(R.id.tv_pronouns);
        tv_friends_count = findViewById(R.id.tv_friends_count);
        tv_user_age = findViewById(R.id.tv_user_age);
        tv_user_name = findViewById(R.id.tv_user_name);

        btn_view_friends = findViewById(R.id.btn_view_friends);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_edit:
                startActivity(new Intent(context, EditProfileActivity.class));
                break;
            case R.id.btn_view_friends:
                startActivity(new Intent(context, FriendsActivity.class));
                break;
        }
    }
}
