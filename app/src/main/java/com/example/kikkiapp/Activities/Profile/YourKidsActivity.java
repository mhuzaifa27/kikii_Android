package com.example.kikkiapp.Activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kikkiapp.Adapters.ChipAdapter;
import com.example.kikkiapp.Adapters.TabsAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetCategory;
import com.example.kikkiapp.Model.ChipModel;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.plumillonforge.android.chipview.OnChipClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourKidsActivity extends AppCompatActivity implements OnChipClickListener {

    private static final String TAG = "YourKidsActivity";
    private Context context = YourKidsActivity.this;
    private List<Chip> chipList = new ArrayList<>();
    private ChipViewAdapter chipViewAdapter;
    private ChipView chip_statuses;
    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetCategory> callbackGetCategoryCall;
    private CallbackGetCategory responseGetCategory;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_kids);

        initComponents();
        getIdentity();

        chip_statuses.setOnChipClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, YourKidsActivity.class));
            }
        });
    }


    @Override
    public void onChipClick(Chip chip) {
        String isChecked = chip.getText();
        chipViewAdapter = new ChipAdapter(context, isChecked);
        chip_statuses.setAdapter(chipViewAdapter);
        chip_statuses.setChipList(chipList);
        //Toast.makeText(this, "chip clicked", Toast.LENGTH_SHORT).show();
    }

    private void getIdentity() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetCategoryCall = api.getCategory(sessionManager.getAccessToken(), Constant.KIDS);
        callbackGetCategoryCall.enqueue(new Callback<CallbackGetCategory>() {
            @Override
            public void onResponse(Call<CallbackGetCategory> call, Response<CallbackGetCategory> response) {
                Log.d(TAG, "onResponse: " + response);
                responseGetCategory = response.body();
                if (responseGetCategory != null) {
                    if (responseGetCategory.getSuccess()) {
                        customLoader.hideIndicator();
                        if (responseGetCategory.getValue().getValueAttr().size() > 0)
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
        for (int i = 0; i < responseGetCategory.getValue().getValueAttr().size(); i++) {
            Log.d(TAG, "setData: " + responseGetCategory.getValue().getValueAttr().get(i));
            chipList.add(new ChipModel(responseGetCategory.getValue().getValueAttr().get(i)));
        }
        chipViewAdapter = new ChipAdapter(context, responseGetCategory.getIsChecked());
        chip_statuses.setAdapter(chipViewAdapter);
        chip_statuses.setChipList(chipList);
    }

    private void initComponents() {
        customLoader = new CustomLoader(this, false);
        sessionManager = new SessionManager(this);

        chip_statuses = findViewById(R.id.chip_statuses);
    }
}

