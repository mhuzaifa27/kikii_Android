package com.example.kikkiapp.Activities;

import androidx.annotation.Nullable;
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
import com.example.kikkiapp.Model.ProfileUser;
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
    public static final int REQUEST_EDIT_PROFILE=230;

    private SelectableRoundedImageView img_user;
    private TextView tv_user_name, tv_user_age, tv_gender, tv_pronouns, tv_distance, tv_bio, tv_friends_count;
    private TextView tv_relationship_status,tv_height,tv_looking_for,tv_cigerate,tv_drink,tv_canabiese,tv_political_views,tv_religion,tv_diet,tv_sign,tv_pet,tv_children;
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
        sessionManager.saveProfileUser(responseGetProfile.getUser());
        ProfileUser user=responseGetProfile.getUser();
        tv_user_name.setText(user.getName());
        tv_friends_count.setText("("+user.getFriends_count()+")");
        if (user.getPronouns() != null)
            tv_pronouns.setText(user.getPronouns());
        if (user.getGenderIdentity() != null)
            tv_gender.setText(user.getGenderIdentity());
       /* if (user.getPronouns() != null)
            tv_distance.setText(user.getPronouns());
        if (user.getPronouns() != null)
            tv_bio.setText(user.getBio());*/
        if (user.getBio() != null)
            tv_bio.setText(user.getBio());
        /***CURIOSITIES**/
        if (user.getRelationshipStatus() != null)
            tv_relationship_status.setText(user.getRelationshipStatus());
        if (user.getHeight() != null)
            tv_height.setText(user.getHeight());
        if (user.getLookingFor() != null)
            tv_looking_for.setText(user.getLookingFor());
        if (user.getSmoke() != null)
            tv_cigerate.setText(user.getSmoke());
        if (user.getDrink() != null)
            tv_drink.setText(user.getDrink());
        if (user.getCannabis() != null)
            tv_canabiese.setText(user.getCannabis());
        if (user.getPoliticalViews() != null)
            tv_political_views.setText(user.getPoliticalViews());
        if (user.getReligion() != null)
            tv_religion.setText(user.getReligion());
        if (user.getDietLike() != null)
            tv_diet.setText(user.getDietLike());
        if (user.getSign() != null)
            tv_sign.setText(user.getSign());
        if (user.getPets() != null)
            tv_pet.setText(user.getPets());
        if (user.getKids() != null)
            tv_children.setText(user.getKids());
        Glide
                .with(context)
                .load(user.getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(img_user);
        tv_user_age.setText(", "+UtilityFunctions.getAge(user.getBirthday()));
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

        tv_relationship_status=findViewById(R.id.tv_relationship_status);
        tv_height=findViewById(R.id.tv_height);
        tv_looking_for=findViewById(R.id.tv_looking_for);
        tv_cigerate=findViewById(R.id.tv_cigerate);
        tv_drink=findViewById(R.id.tv_drink);
        tv_canabiese=findViewById(R.id.tv_canabiese);
        tv_political_views=findViewById(R.id.tv_political_views);
        tv_religion=findViewById(R.id.tv_religion);
        tv_diet=findViewById(R.id.tv_diet);
        tv_sign=findViewById(R.id.tv_sign);
        tv_pet=findViewById(R.id.tv_pet);
        tv_children=findViewById(R.id.tv_children);

        btn_view_friends = findViewById(R.id.btn_view_friends);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_edit:
                startActivityForResult(new Intent(context, EditProfileActivity.class),REQUEST_EDIT_PROFILE);
                break;
            case R.id.btn_view_friends:
                startActivity(new Intent(context, FriendsActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_EDIT_PROFILE && resultCode==RESULT_OK){
            getUserProfile();
        }
    }
}
