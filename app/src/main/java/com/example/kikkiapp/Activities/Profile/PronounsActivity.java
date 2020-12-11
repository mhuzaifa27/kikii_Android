package com.example.kikkiapp.Activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kikkiapp.Adapters.IdentityAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetCategory;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constants;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CommonMethods;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PronounsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PronounsActivity";
    private Context context= PronounsActivity.this;
    
    private IdentityAdapter identityAdapter;
    private RecyclerView rv_pronouns;
    private List<String> pronounsList =new ArrayList<>();
    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetCategory> callbackGetCategoryCall;
    private CallbackGetCategory responseGetCategory;

    private String isChecked;
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouns);

        initComponents();
        getIdentity();

        img_back.setOnClickListener(this);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra(Constants.PRONOUNS,isChecked);
                setResult(RESULT_OK,intent);
                onBackPressed();
            }
        });
    }

    private void getIdentity() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetCategoryCall = api.getCategory(sessionManager.getAccessToken(), Constants.PRONOUNS);
        callbackGetCategoryCall.enqueue(new Callback<CallbackGetCategory>() {
            @Override
            public void onResponse(Call<CallbackGetCategory> call, Response<CallbackGetCategory> response) {
                Log.d(TAG, "onResponse: " + response);
                responseGetCategory = response.body();
                if (responseGetCategory != null) {
                    if (responseGetCategory.getSuccess()) {
                        customLoader.hideIndicator();
                        if (responseGetCategory.getValue() != null){
                            if (responseGetCategory.getValue().getValueAttr().size() > 0)
                                setData();
                        }
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
        pronounsList =responseGetCategory.getValue().getValueAttr();
        identityAdapter=new IdentityAdapter(pronounsList,context,responseGetCategory.getIsChecked());
        rv_pronouns.setAdapter(identityAdapter);
        identityAdapter.setOnClickListener(new IdentityAdapter.IClicks() {
            @Override
            public void onClickListener(View view, String s) {
                isChecked=s;
            }
        });
    }
    private void initComponents() {
        customLoader=new CustomLoader(this,false);
        sessionManager=new SessionManager(this);

        rv_pronouns =findViewById(R.id.rv_pronouns);
        rv_pronouns.setLayoutManager(new LinearLayoutManager(context));

        img_back=findViewById(R.id.img_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                CommonMethods.goBack(this);
                break;
        }
    }
}
