package com.example.kikkiapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.Profile.AddBioActivity;
import com.example.kikkiapp.Activities.Profile.GenderIdentityActivity;
import com.example.kikkiapp.Activities.Profile.PronounsActivity;
import com.example.kikkiapp.Activities.Profile.SexualIdentityActivity;
import com.example.kikkiapp.Model.ProfilePic;
import com.example.kikkiapp.Model.ProfileUser;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowSelectImageBottomSheet;
import com.joooonho.SelectableRoundedImageView;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EditProfileActivity";
    private Context context = EditProfileActivity.this;
    private Activity activity = EditProfileActivity.this;

    private SelectableRoundedImageView img_user, img_selected_1, img_selected_2, img_selected_3, img_selected_4, img_selected_5, img_selected_6, img_selected_7, img_selected_8;
    private ImageView img_select_1, img_select_2, img_select_3, img_select_4, img_select_5, img_select_6, img_select_7, img_select_8;
    private static int setImageOn = 0;
    private LinearLayout ll_gender_identity, ll_sexual_identity, ll_pronouns, ll_bio;

    private List<String> mediaPaths=new ArrayList<>();
    private List<MultipartBody.Part> imagesList = new ArrayList<>();
    private ProfileUser user;
    private SessionManager sessionManager;
    private CustomLoader customLoader;

    private TextView tv_gender_identity,tv_sexual_identity,tv_pronouns,tv_bio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initComponents();
        setData();

        img_select_1.setOnClickListener(this);
        img_select_2.setOnClickListener(this);
        img_select_3.setOnClickListener(this);
        img_select_4.setOnClickListener(this);
        img_select_5.setOnClickListener(this);
        img_select_6.setOnClickListener(this);
        img_select_7.setOnClickListener(this);
        img_select_8.setOnClickListener(this);

        ll_gender_identity.setOnClickListener(this);
        ll_sexual_identity.setOnClickListener(this);
        ll_pronouns.setOnClickListener(this);
        ll_bio.setOnClickListener(this);

    }

    private void setData() {
        Glide
                .with(context)
                .load(user.getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(img_user);

        List<ProfilePic> profilePics=user.getProfilePics();
        if(profilePics.size()>0){
            for (int i = 0; i <profilePics.size() ; i++) {
                setUrlToImage(profilePics.get(i).getPath(),i+1);
            }
        }
    }

    private void initComponents() {
        customLoader=new CustomLoader(this,false);
        sessionManager=new SessionManager(this);

        user=sessionManager.getProfileUser();

        tv_gender_identity=findViewById(R.id.tv_gender_identity);
        tv_sexual_identity=findViewById(R.id.tv_sexual_identity);
        tv_pronouns=findViewById(R.id.tv_pronouns);
        tv_bio=findViewById(R.id.tv_bio);

        ll_gender_identity = findViewById(R.id.ll_gender_identity);
        ll_sexual_identity = findViewById(R.id.ll_sexual_identity);
        ll_pronouns = findViewById(R.id.ll_pronouns);
        ll_bio = findViewById(R.id.ll_bio);

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
            case R.id.ll_gender_identity:
                startActivity(new Intent(context, GenderIdentityActivity.class));
                break;
            case R.id.ll_sexual_identity:
                startActivity(new Intent(context, SexualIdentityActivity.class));
                break;
            case R.id.ll_pronouns:
                startActivity(new Intent(context, PronounsActivity.class));
                break;
            case R.id.ll_bio:
                startActivity(new Intent(context, AddBioActivity.class));
                break;
            case R.id.img_select_1:
                setImageOn = 1;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_1, Constant.SINGLE);
                break;
            case R.id.img_select_2:
                setImageOn = 2;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_2, Constant.SINGLE);
                break;
            case R.id.img_select_3:
                setImageOn = 3;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_3, Constant.SINGLE);
                break;
            case R.id.img_select_4:
                setImageOn = 4;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_4, Constant.SINGLE);
                break;
            case R.id.img_select_5:
                setImageOn = 5;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_5, Constant.SINGLE);
                break;
            case R.id.img_select_6:
                setImageOn = 6;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_6, Constant.SINGLE);
                break;
            case R.id.img_select_7:
                setImageOn = 7;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_7, Constant.SINGLE);
                break;
            case R.id.img_select_8:
                setImageOn = 8;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_8, Constant.SINGLE);
                break;
        }
    }

    private void setUriToImage(Uri uri) {
        switch (setImageOn) {
            case 1:
                img_selected_1.setImageURI(uri);
                img_select_1.setVisibility(View.GONE);
                break;
            case 2:
                img_selected_2.setImageURI(uri);
                img_select_2.setVisibility(View.GONE);

                break;
            case 3:
                img_selected_3.setImageURI(uri);
                img_select_3.setVisibility(View.GONE);

                break;
            case 4:
                img_selected_4.setImageURI(uri);
                img_select_4.setVisibility(View.GONE);
                break;
            case 5:
                img_selected_5.setImageURI(uri);
                img_select_5.setVisibility(View.GONE);

                break;
            case 6:
                img_selected_6.setImageURI(uri);
                img_select_6.setVisibility(View.GONE);
                break;
            case 7:
                img_selected_7.setImageURI(uri);
                img_select_7.setVisibility(View.GONE);
                break;
            case 8:
                img_selected_8.setImageURI(uri);
                img_select_8.setVisibility(View.GONE);
                break;
        }
    }

    private void setUriToImage(Bitmap bitmap, int setImageOn) {
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
    private void setUrlToImage(String path, int setImageOn) {
        switch (setImageOn) {
            case 1:
                Glide
                        .with(context)
                        .load(path)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(img_selected_1);
                img_select_1.setVisibility(View.GONE);
                break;
            case 2:
                Glide
                        .with(context)
                        .load(path)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(img_selected_2);
                img_select_2.setVisibility(View.GONE);

                break;
            case 3:
                Glide
                        .with(context)
                        .load(path)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(img_selected_3);
                img_select_3.setVisibility(View.GONE);

                break;
            case 4:
                Glide
                        .with(context)
                        .load(path)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(img_selected_4);
                img_select_4.setVisibility(View.GONE);
                break;
            case 5:
                Glide
                        .with(context)
                        .load(path)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(img_selected_5);
                img_select_5.setVisibility(View.GONE);

                break;
            case 6:
                Glide
                        .with(context)
                        .load(path)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(img_selected_6);
                img_select_6.setVisibility(View.GONE);
                break;
            case 7:
                Glide
                        .with(context)
                        .load(path)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(img_selected_7);
                img_select_7.setVisibility(View.GONE);
                break;
            case 8:
                Glide
                        .with(context)
                        .load(path)
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(img_selected_8);
                img_select_8.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> temp = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            if (setImageOn >= mediaPaths.size())
                mediaPaths.add(temp.get(0));
            else if (setImageOn < mediaPaths.size())
                mediaPaths.add(setImageOn, temp.get(0));
            setUriToImage(Uri.parse(temp.get(0)));

            Log.d("hhhh", "onActivityResult: " + mediaPaths.size());

        }
    }
}
