package com.example.kikkiapp.Activities.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kikkiapp.Adapters.IdentityAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetCategory;
import com.example.kikkiapp.Callbacks.CallbackGetProfile;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenderIdentityActivity extends AppCompatActivity {

    private static final String TAG = "GenderIdentity";
    private Context context= GenderIdentityActivity.this;

    private RecyclerView rv_gender_identities;
    private IdentityAdapter identityAdapter;
    private List<String> genderIdentitiesList=new ArrayList<>();
    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetCategory> callbackGetCategoryCall;
    private CallbackGetCategory responseGetCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_identity);

        initComponents();
        getIdentity();

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,SexualIdentityActivity.class));
            }
        });
    }

    private void getIdentity() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetCategoryCall = api.getCategory(sessionManager.getAccessToken(), Constant.GENDER_IDENTITY);
        callbackGetCategoryCall.enqueue(new Callback<CallbackGetCategory>() {
            @Override
            public void onResponse(Call<CallbackGetCategory> call, Response<CallbackGetCategory> response) {
                Log.d(TAG, "onResponse: " + response);
                responseGetCategory = response.body();
                if (responseGetCategory != null) {
                    if (responseGetCategory.getSuccess()) {
                        customLoader.hideIndicator();
                        setData();
                    } else {
                        Log.d(TAG, "onResponse: " + responseGetCategory.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseGetCategory.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetCategory> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setData() {
        genderIdentitiesList=responseGetCategory.getValue().getValueAttr();
        identityAdapter=new IdentityAdapter(genderIdentitiesList,context,responseGetCategory.getIsChecked());
        rv_gender_identities.setAdapter(identityAdapter);
    }
    private void initComponents() {
        customLoader=new CustomLoader(this,false);
        sessionManager=new SessionManager(this);

        rv_gender_identities=findViewById(R.id.rv_gender_identities);
        rv_gender_identities.setLayoutManager(new LinearLayoutManager(context));
    }
}
