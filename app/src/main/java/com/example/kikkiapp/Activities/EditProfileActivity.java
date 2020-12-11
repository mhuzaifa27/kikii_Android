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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.Profile.AddBioActivity;
import com.example.kikkiapp.Activities.Profile.DoYouDrinkActivity;
import com.example.kikkiapp.Activities.Profile.DoYouSmokeActivity;
import com.example.kikkiapp.Activities.Profile.DoYouUseCannabisActivity;
import com.example.kikkiapp.Activities.Profile.GenderIdentityActivity;
import com.example.kikkiapp.Activities.Profile.LookingForActivity;
import com.example.kikkiapp.Activities.Profile.PoliticalViewsActivity;
import com.example.kikkiapp.Activities.Profile.PronounsActivity;
import com.example.kikkiapp.Activities.Profile.RelationshipStatusActivity;
import com.example.kikkiapp.Activities.Profile.SetHeightActivity;
import com.example.kikkiapp.Activities.Profile.SexualIdentityActivity;
import com.example.kikkiapp.Activities.Profile.YourDietActivity;
import com.example.kikkiapp.Activities.Profile.YourKidsActivity;
import com.example.kikkiapp.Activities.Profile.YourPetsActivity;
import com.example.kikkiapp.Activities.Profile.YourReligionActivity;
import com.example.kikkiapp.Activities.Profile.YourSignActivity;
import com.example.kikkiapp.Adapters.CuriositiesAdapter;
import com.example.kikkiapp.Callbacks.CallbackUpdateProfile;
import com.example.kikkiapp.Model.CuriosityChipModel;
import com.example.kikkiapp.Model.ProfilePic;
import com.example.kikkiapp.Model.ProfileUser;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constants;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CommonMethods;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SelectImage;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.example.kikkiapp.Utils.ShowSelectImageBottomSheet;
import com.joooonho.SelectableRoundedImageView;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.OnChipClickListener;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, OnChipClickListener {

    private static final String TAG = "EditProfileActivity";
    private Context context = EditProfileActivity.this;
    private Activity activity = EditProfileActivity.this;

    public static final int REQUEST_GENDER_IDENTITY = 400;
    public static final int REQUEST_SEXUAL_IDENTITY = 410;
    public static final int REQUEST_PRONOUNS = 420;
    public static final int REQUEST_ADD_BIO = 430;
    public static final int REQUEST_RELATIONSHIP_STATUS = 440;
    public static final int REQUEST_HEIGHT = 450;
    public static final int REQUEST_LOOKING_FOR = 460;
    public static final int REQUEST_DO_YOU_DRINK = 470;
    public static final int REQUEST_DO_YOU_SMOKE = 480;
    public static final int REQUEST_CANNABIS = 490;
    public static final int REQUEST_POLITICAL_VIEWS = 500;
    public static final int REQUEST_RELIGION = 510;
    public static final int REQUEST_DIET_LIKE = 520;
    public static final int REQUEST_YOUR_SIGN = 530;
    public static final int REQUEST_PETS = 540;
    public static final int REQUEST_KIDS = 550;

    private SelectableRoundedImageView img_user, img_selected_1, img_selected_2, img_selected_3, img_selected_4, img_selected_5, img_selected_6, img_selected_7, img_selected_8;
    private ImageView img_select_1, img_select_2, img_select_3, img_select_4, img_select_5, img_select_6, img_select_7, img_select_8, img_ok;
    private static int setImageOn = 0;
    private LinearLayout ll_gender_identity, ll_sexual_identity, ll_pronouns, ll_bio;

    private List<String> mediaPaths = new ArrayList<>();
    private List<MultipartBody.Part> imagesList = new ArrayList<>();
    private ProfileUser user;
    private SessionManager sessionManager;
    private CustomLoader customLoader;

    private List<Chip> chipList = new ArrayList<>();
    private CuriositiesAdapter curiositiesAdapter;
    private ChipView chip_curiosities;

    private TextView tv_gender_identity, tv_sexual_identity, tv_pronouns, tv_bio;

    private Map<String, String> updateProfileParams = new HashMap<>();

    private Call<CallbackUpdateProfile> callbackUpdateProfile;
    private CallbackUpdateProfile responseUpdateProfile;

    private Map<String, RequestBody> editProfileMultipartParams = new HashMap<>();
    private ImageView img_back;
    private Switch swch_location, swch_incognito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initComponents();
        setData();

        chip_curiosities.setOnChipClickListener(this);

        img_select_1.setOnClickListener(this);
        img_select_2.setOnClickListener(this);
        img_select_3.setOnClickListener(this);
        img_select_4.setOnClickListener(this);
        img_select_5.setOnClickListener(this);
        img_select_6.setOnClickListener(this);
        img_select_7.setOnClickListener(this);
        img_select_8.setOnClickListener(this);

        img_ok.setOnClickListener(this);
        img_back.setOnClickListener(this);

        img_user.setOnClickListener(this);

        ll_gender_identity.setOnClickListener(this);
        ll_sexual_identity.setOnClickListener(this);
        ll_pronouns.setOnClickListener(this);
        ll_bio.setOnClickListener(this);

        swch_location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) updateProfileParams.put(Constants.SHOW_LOCATION, "1");
                else updateProfileParams.put(Constants.SHOW_LOCATION, "0");
            }
        });
        swch_incognito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) updateProfileParams.put(Constants.INCOGNITO, "1");
                else updateProfileParams.put(Constants.INCOGNITO, "0");
            }
        });
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

        List<ProfilePic> profilePics = user.getProfilePics();
        if (profilePics.size() > 0) {
            for (int i = 0; i < profilePics.size(); i++) {
                setUrlToImage(profilePics.get(i).getPath(), i + 1);
            }
        }

        if (user.getGenderIdentity() != null)
            tv_gender_identity.setText(user.getGenderIdentity());
        if (user.getSexualIdentity() != null)
            tv_sexual_identity.setText(user.getGenderIdentity());
        if (user.getPronouns() != null)
            tv_pronouns.setText(user.getPronouns());
        if (user.getBio() != null)
            tv_bio.setText(user.getBio());

        if (user.getShow_location() == 1)
            swch_location.setChecked(true);
        else
            swch_location.setChecked(false);

        if (user.getIncognito() == 1)
            swch_incognito.setChecked(true);
        else
            swch_incognito.setChecked(false);

        setCuriosities();
    }

    private void setCuriosities() {
        if (user.getRelationshipStatus() != null)
            chipList.add(new CuriosityChipModel(user.getRelationshipStatus(), 1));
        else
            chipList.add(new CuriosityChipModel("RelationShip Status?", 1));
        if (user.getHeight() != null)
            chipList.add(new CuriosityChipModel(user.getHeight(), 2));
        else
            chipList.add(new CuriosityChipModel("Height?", 2));
        if (user.getLookingFor() != null)
            chipList.add(new CuriosityChipModel(user.getLookingFor(), 3));
        else
            chipList.add(new CuriosityChipModel("Looking For?", 3));
        if (user.getDrink() != null)
            chipList.add(new CuriosityChipModel(user.getDrink(), 4));
        else
            chipList.add(new CuriosityChipModel("Do you drink?", 4));
        if (user.getSmoke() != null)
            chipList.add(new CuriosityChipModel(user.getSmoke(), 5));
        else
            chipList.add(new CuriosityChipModel("Do you smoke?", 5));
        if (user.getCannabis() != null)
            chipList.add(new CuriosityChipModel(user.getCannabis(), 6));
        else
            chipList.add(new CuriosityChipModel("Do you use Cannabis?", 6));
        if (user.getPoliticalViews() != null)
            chipList.add(new CuriosityChipModel(user.getPoliticalViews(), 7));
        else
            chipList.add(new CuriosityChipModel("Political views?", 7));
        if (user.getReligion() != null)
            chipList.add(new CuriosityChipModel(user.getReligion(), 8));
        else
            chipList.add(new CuriosityChipModel("Your religion?", 8));
        if (user.getDietLike() != null)
            chipList.add(new CuriosityChipModel(user.getDietLike(), 9));
        else
            chipList.add(new CuriosityChipModel("Diet like?", 9));
        if (user.getSign() != null)
            chipList.add(new CuriosityChipModel(user.getSign(), 10));
        else
            chipList.add(new CuriosityChipModel("Your sign?", 10));
        if (user.getPets() != null)
            chipList.add(new CuriosityChipModel(user.getPets(), 11));
        else
            chipList.add(new CuriosityChipModel("Any pets?", 11));
        if (user.getKids() != null)
            chipList.add(new CuriosityChipModel(user.getKids(), 12));
        else
            chipList.add(new CuriosityChipModel("Want Kids?", 12));

        curiositiesAdapter = new CuriositiesAdapter(context);
        chip_curiosities.setAdapter(curiositiesAdapter);
        chip_curiosities.setChipList(chipList);

    }

    private void initComponents() {
        customLoader = new CustomLoader(this, false);
        sessionManager = new SessionManager(this);

        user = sessionManager.getProfileUser();

        tv_gender_identity = findViewById(R.id.tv_gender_identity);
        tv_sexual_identity = findViewById(R.id.tv_sexual_identity);
        tv_pronouns = findViewById(R.id.tv_pronoun_2);
        tv_bio = findViewById(R.id.tv_bio);

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

        img_ok = findViewById(R.id.img_ok);
        img_back = findViewById(R.id.img_back);

        chip_curiosities = findViewById(R.id.chip_curiosities);

        swch_location = findViewById(R.id.swch_location);
        swch_incognito = findViewById(R.id.swch_incognito);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_gender_identity:
                startActivityForResult(new Intent(context, GenderIdentityActivity.class), REQUEST_GENDER_IDENTITY);
                break;
            case R.id.ll_sexual_identity:
                startActivityForResult(new Intent(context, SexualIdentityActivity.class), REQUEST_SEXUAL_IDENTITY);
                break;
            case R.id.ll_pronouns:
                startActivityForResult(new Intent(context, PronounsActivity.class), REQUEST_PRONOUNS);
                break;
            case R.id.ll_bio:
                startActivityForResult(new Intent(context, AddBioActivity.class), REQUEST_ADD_BIO);
                break;
            case R.id.img_select_1:
                setImageOn = 1;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_1, Constants.SINGLE);
                break;
            case R.id.img_select_2:
                setImageOn = 2;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_2, Constants.SINGLE);
                break;
            case R.id.img_select_3:
                setImageOn = 3;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_3, Constants.SINGLE);
                break;
            case R.id.img_select_4:
                setImageOn = 4;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_4, Constants.SINGLE);
                break;
            case R.id.img_select_5:
                setImageOn = 5;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_5, Constants.SINGLE);
                break;
            case R.id.img_select_6:
                setImageOn = 6;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_6, Constants.SINGLE);
                break;
            case R.id.img_select_7:
                setImageOn = 7;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_7, Constants.SINGLE);
                break;
            case R.id.img_select_8:
                setImageOn = 8;
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_8, Constants.SINGLE);
                break;
            case R.id.img_ok:
                if (updateProfileParams.size() > 0) {
                    if (mediaPaths.size() > 0) {
                        uploadFiles();
                    } else {
                        updateProfile();
                    }
                }
                break;
            case R.id.img_back:
                CommonMethods.goBack(this);
                break;
            case R.id.img_user:
                //setImageOn=-1;
                //ShowSelectImageBottomSheet.showDialog(activity,getWindow().getDecorView().getRootView(),Constant.SINGLE);
                break;
        }
    }

    private void uploadFiles() {
        for (int i = 0; i < mediaPaths.size(); i++) {
            imagesList.add(SelectImage.prepareFilePart("new_pics[]", mediaPaths.get(i)));
        }
        updateProfileWithImages();
    }

    private void updateProfileWithImages() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "createPost: " + sessionManager.getAccessToken());
        callbackUpdateProfile = api.updateProfileWithImages(sessionManager.getAccessToken(), updateProfileParams, imagesList);
        callbackUpdateProfile.enqueue(new Callback<CallbackUpdateProfile>() {
            @Override
            public void onResponse(Call<CallbackUpdateProfile> call, Response<CallbackUpdateProfile> response) {
                Log.d(TAG, "onResponse: " + response);
                responseUpdateProfile = response.body();
                if (responseUpdateProfile != null) {
                    customLoader.hideIndicator();
                    if (responseUpdateProfile.getSuccess()) {
                        Log.d(TAG, "onResponse: " + responseUpdateProfile.getMessage());
                        Toast.makeText(context, responseUpdateProfile.getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Log.d(TAG, "onResponse: " + responseUpdateProfile.getMessage());
                        Toast.makeText(context, responseUpdateProfile.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackUpdateProfile> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setUriToImage(Uri uri) {
        switch (setImageOn) {
            case -1:
                img_user.setImageURI(uri);
                break;
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
            if (setImageOn == -1) {
                setUriToImage(Uri.parse(temp.get(0)));
            } else {
                if (setImageOn >= mediaPaths.size())
                    mediaPaths.add(temp.get(0));
                else if (setImageOn < mediaPaths.size())
                    mediaPaths.add(setImageOn, temp.get(0));
                setUriToImage(Uri.parse(temp.get(0)));
            }

            Log.d("hhhh", "onActivityResult: " + mediaPaths.size());

        }
        if (requestCode == REQUEST_GENDER_IDENTITY && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.GENDER_IDENTITY);
                tv_gender_identity.setText(cat);
                updateProfileParams.put(Constants.GENDER_IDENTITY, cat);
            }
        }
        if (requestCode == REQUEST_SEXUAL_IDENTITY && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.SEXUAL_IDENTITY);
                tv_sexual_identity.setText(cat);
                updateProfileParams.put(Constants.SEXUAL_IDENTITY, cat);
            }
        }
        if (requestCode == REQUEST_PRONOUNS && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.PRONOUNS);
                tv_pronouns.setText(cat);
                updateProfileParams.put(Constants.PRONOUNS, cat);
            }
        }
        if (requestCode == REQUEST_ADD_BIO && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.BIO);
                tv_bio.setText(cat);
                updateProfileParams.put(Constants.BIO, cat);
            }
        }
        if (requestCode == REQUEST_RELATIONSHIP_STATUS && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.RELATIONSHIP_STATUS);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(0);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.RELATIONSHIP_STATUS, cat);
            }
        }
        if (requestCode == REQUEST_HEIGHT && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.HEIGHT);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(1);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.HEIGHT, cat);
            }
        }
        if (requestCode == REQUEST_LOOKING_FOR && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.LOOKING_FOR);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(2);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.LOOKING_FOR, cat);
            }
        }
        if (requestCode == REQUEST_DO_YOU_DRINK && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.DRINK);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(3);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.DRINK, cat);
            }
        }
        if (requestCode == REQUEST_CANNABIS && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.CANNABIS);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(5);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.CANNABIS, cat);
            }
        }
        if (requestCode == REQUEST_POLITICAL_VIEWS && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.POLITICAL_VIEWS);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(6);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.POLITICAL_VIEWS, cat);
            }
        }
        if (requestCode == REQUEST_RELIGION && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.RELIGION);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(7);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.RELIGION, cat);

            }
        }
        if (requestCode == REQUEST_DIET_LIKE && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.DIET_LIKE);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(8);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.DIET_LIKE, cat);
            }
        }
        if (requestCode == REQUEST_YOUR_SIGN && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.SIGN);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(9);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.SIGN, cat);
            }
        }
        if (requestCode == REQUEST_PETS && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.PETS);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(10);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.PETS, cat);
            }
        }
        if (requestCode == REQUEST_DO_YOU_SMOKE && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.SMOKE);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(4);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.SMOKE, cat);
            }
        }
        if (requestCode == REQUEST_KIDS && resultCode == RESULT_OK) {
            if (data != null) {
                String cat = data.getStringExtra(Constants.KIDS);
                CuriosityChipModel chip = (CuriosityChipModel) chipList.get(11);
                chip.setmName(cat);
                curiositiesAdapter = new CuriositiesAdapter(context);
                chip_curiosities.setAdapter(curiositiesAdapter);
                chip_curiosities.setChipList(chipList);

                updateProfileParams.put(Constants.KIDS, cat);
            }
        }
    }

    @Override
    public void onChipClick(Chip chip) {
        CuriosityChipModel curiosityChipModel = (CuriosityChipModel) chip;
        if (curiosityChipModel != null) {
            Intent intent;
            switch (curiosityChipModel.getPosition()) {
                case 1:
                    intent = new Intent(context, RelationshipStatusActivity.class);
                    if (user.getRelationshipStatus() != null)
                        intent.putExtra(Constants.RELATIONSHIP_STATUS, user.getRelationshipStatus());
                    startActivityForResult(intent, REQUEST_RELATIONSHIP_STATUS);
                    break;
                case 2:
                    intent = new Intent(context, SetHeightActivity.class);
                    if (user.getHeight() != null)
                        intent.putExtra(Constants.HEIGHT, user.getHeight());
                    startActivityForResult(intent, REQUEST_HEIGHT);
                    break;
                case 3:
                    intent = new Intent(context, LookingForActivity.class);
                    if (user.getLookingFor() != null)
                        intent.putExtra(Constants.LOOKING_FOR, user.getLookingFor());
                    startActivityForResult(intent, REQUEST_LOOKING_FOR);
                    break;
                case 4:
                    intent = new Intent(context, DoYouDrinkActivity.class);
                    if (user.getDrink() != null)
                        intent.putExtra(Constants.DRINK, user.getDrink());
                    startActivityForResult(intent, REQUEST_DO_YOU_DRINK);
                    break;
                case 5:
                    intent = new Intent(context, DoYouSmokeActivity.class);
                    if (user.getSmoke() != null)
                        intent.putExtra(Constants.SMOKE, user.getSmoke());
                    startActivityForResult(intent, REQUEST_DO_YOU_SMOKE);
                    break;
                case 6:
                    intent = new Intent(context, DoYouUseCannabisActivity.class);
                    if (user.getCannabis() != null)
                        intent.putExtra(Constants.CANNABIS, user.getCannabis());
                    startActivityForResult(intent, REQUEST_CANNABIS);
                    break;
                case 7:
                    intent = new Intent(context, PoliticalViewsActivity.class);
                    if (user.getPoliticalViews() != null)
                        intent.putExtra(Constants.POLITICAL_VIEWS, user.getPoliticalViews());
                    startActivityForResult(intent, REQUEST_POLITICAL_VIEWS);
                    break;
                case 8:
                    intent = new Intent(context, YourReligionActivity.class);
                    if (user.getReligion() != null)
                        intent.putExtra(Constants.RELIGION, user.getReligion());
                    startActivityForResult(intent, REQUEST_RELIGION);
                    break;
                case 9:
                    intent = new Intent(context, YourDietActivity.class);
                    if (user.getDietLike() != null)
                        intent.putExtra(Constants.DIET_LIKE, user.getDietLike());
                    startActivityForResult(intent, REQUEST_DIET_LIKE);
                    break;
                case 10:
                    intent = new Intent(context, YourSignActivity.class);
                    if (user.getSign() != null)
                        intent.putExtra(Constants.SIGN, user.getSign());
                    startActivityForResult(intent, REQUEST_YOUR_SIGN);
                    break;
                case 11:
                    intent = new Intent(context, YourPetsActivity.class);
                    if (user.getPets() != null)
                        intent.putExtra(Constants.PETS, user.getPets());
                    startActivityForResult(intent, REQUEST_PETS);
                    break;
                case 12:
                    intent = new Intent(context, YourKidsActivity.class);
                    if (user.getKids() != null)
                        intent.putExtra(Constants.KIDS, user.getKids());
                    startActivityForResult(intent, REQUEST_KIDS);
                    break;

            }
        }
    }

    private void updateProfile() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackUpdateProfile = api.updateProfile(sessionManager.getAccessToken(), updateProfileParams);
        callbackUpdateProfile.enqueue(new Callback<CallbackUpdateProfile>() {
            @Override
            public void onResponse(Call<CallbackUpdateProfile> call, Response<CallbackUpdateProfile> response) {
                Log.d(TAG, "onResponse: " + response);
                responseUpdateProfile = response.body();
                if (responseUpdateProfile != null) {
                    if (responseUpdateProfile.getSuccess()) {
                        setResult(RESULT_OK);
                        onBackPressed();
                    } else {
                        Log.d(TAG, "onResponse: " + responseUpdateProfile.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseUpdateProfile.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackUpdateProfile> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }
}
