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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.example.kikkiapp.Activities.Profile.SetHeightActivity;
import com.example.kikkiapp.Adapters.PremiumFiltersAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetFilters;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constants;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltersActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FiltersActivity";
    private Context context = FiltersActivity.this;

    public static final int REQUEST_SET_HEIGHT = 220;

    private RecyclerView rv_premium_filters;
    private PremiumFiltersAdapter premiumFiltersAdapter;
    private List<String> premiumFilterList = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    private CrystalRangeSeekbar rsb_age;
    private CrystalSeekbar sb_distance;
    private TextView tv_start_age, tv_end_age, tv_distance_value, tv_kms, tv_miles, tv_height, tv_height_value, tv_any_age;
    private double currentDistance;
    private ImageView img_ok;
    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackStatus> callbackUpdateFilters;
    private CallbackStatus responseUpdateFilters;

    private Call<CallbackGetFilters> callbackGetFiltersCall;
    private CallbackGetFilters responseGetFilters;

    private Map<String, String> updateFiltersParam = new HashMap<>();
    private String height = "", distanceIn = "km";
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        initComponents();
        getFilters();

        premiumFilterList.add("No");
        premiumFilterList.add("Occasionally");
        premiumFilterList.add("Socially");
        premiumFilterList.add("Prefer not to say");

        tv_height.setOnClickListener(this);
        tv_any_age.setOnClickListener(this);
        tv_kms.setOnClickListener(this);
        tv_miles.setOnClickListener(this);
        img_ok.setOnClickListener(this);
        img_back.setOnClickListener(this);

        rsb_age.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tv_start_age.setText(String.valueOf(minValue));
                tv_end_age.setText(String.valueOf(maxValue));
            }
        });
        sb_distance.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                currentDistance = Double.parseDouble(String.valueOf(value));
                tv_distance_value.setText(String.valueOf(value));
            }
        });
    }

    private void getFilters() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetFiltersCall = api.getFilters(sessionManager.getAccessToken());
        callbackGetFiltersCall.enqueue(new Callback<CallbackGetFilters>() {
            @Override
            public void onResponse(Call<CallbackGetFilters> call, Response<CallbackGetFilters> response) {
                Log.d(TAG, "onResponse: " + response);
                responseGetFilters = response.body();
                if (responseGetFilters != null) {
                    if (responseGetFilters.getSuccess()) {
                        customLoader.hideIndicator();
                        if(responseGetFilters.getFilters()!=null){
                            setData();
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + responseUpdateFilters.getMessage());
                        customLoader.hideIndicator();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetFilters> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setData() {
        if (responseGetFilters.getFilters().getFromAge() != null) {
            rsb_age.setMinStartValue(Integer.parseInt(responseGetFilters.getFilters().getFromAge()));
            tv_start_age.setText(responseGetFilters.getFilters().getFromAge());
        }
        if (responseGetFilters.getFilters().getToAge() != null) {
            rsb_age.setMaxStartValue(Integer.parseInt(responseGetFilters.getFilters().getToAge()));
            tv_end_age.setText(responseGetFilters.getFilters().getToAge());
        }

        if (responseGetFilters.getFilters().getDistance() != null) {
            tv_distance_value.setText(responseGetFilters.getFilters().getDistance());
            sb_distance.setMinStartValue(Integer.parseInt(responseGetFilters.getFilters().getDistance()));
        }

        if (responseGetFilters.getFilters().getDistanceIn() != null) {
            if (responseGetFilters.getFilters().getDistanceIn().equalsIgnoreCase("mi")) {
                tv_kms.setTextColor(getResources().getColor(R.color.grey));
                tv_miles.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                distanceIn = "mi";
            } else {
                tv_kms.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_miles.setTextColor(getResources().getColor(R.color.grey));
                distanceIn = "km";
            }
        }
        if (responseGetFilters.getFilters().getHeight() != null)
            tv_height_value.setText(responseGetFilters.getFilters().getHeight());

    }

    private void initComponents() {
        customLoader = new CustomLoader(this, false);
        sessionManager = new SessionManager(this);

        rv_premium_filters = findViewById(R.id.rv_premium_filters);
        gridLayoutManager = new GridLayoutManager(context, 3);
        rv_premium_filters.setLayoutManager(gridLayoutManager);
        rv_premium_filters.setAdapter(new PremiumFiltersAdapter(premiumFilterList, context));

        rsb_age = findViewById(R.id.rsb_age);
        sb_distance = findViewById(R.id.sb_distance);

        tv_start_age = findViewById(R.id.tv_start_age);
        tv_end_age = findViewById(R.id.tv_end_age);
        tv_distance_value = findViewById(R.id.tv_distance_value);
        tv_miles = findViewById(R.id.tv_miles);
        tv_kms = findViewById(R.id.tv_kms);
        tv_height = findViewById(R.id.tv_height);
        tv_height_value = findViewById(R.id.tv_height_value);
        tv_any_age = findViewById(R.id.tv_any_age);

        img_ok = findViewById(R.id.img_ok);
        img_back=findViewById(R.id.img_back);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SET_HEIGHT && resultCode == RESULT_OK) {
            if (data != null) {
                String value = data.getStringExtra(Constants.HEIGHT);
                tv_height_value.setText(value);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_any_age:
                tv_height_value.setText("Any Height");
                break;
            case R.id.tv_height:
                startActivityForResult(new Intent(context, SetHeightActivity.class), REQUEST_SET_HEIGHT);
                break;
            case R.id.tv_kms:
                tv_kms.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tv_miles.setTextColor(getResources().getColor(R.color.grey));
                distanceIn = "km";
                break;
            case R.id.tv_miles:
                tv_kms.setTextColor(getResources().getColor(R.color.grey));
                tv_miles.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                distanceIn = "mi";
                break;
            case R.id.img_ok:
                if (tv_height_value.getText().toString().equalsIgnoreCase("Any Height"))
                    height = "";
                else
                    height = tv_height_value.getText().toString();

                updateFiltersParam.put(Constants.FROM_AGE, tv_start_age.getText().toString());
                updateFiltersParam.put(Constants.TO_AGE, tv_end_age.getText().toString());
                updateFiltersParam.put(Constants.DISTANCE, tv_distance_value.getText().toString());
                updateFiltersParam.put(Constants.HEIGHT, height);
                updateFiltersParam.put(Constants.DISTANCE_IN, distanceIn);

                updateFilters();
                break;
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    private void updateFilters() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackUpdateFilters = api.updateFilters(sessionManager.getAccessToken(), updateFiltersParam);
        callbackUpdateFilters.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                responseUpdateFilters = response.body();
                if (responseUpdateFilters != null) {
                    if (responseUpdateFilters.getSuccess()) {
                        customLoader.hideIndicator();
                        onBackPressed();
                    } else {
                        Log.d(TAG, "onResponse: " + responseUpdateFilters.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseUpdateFilters.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
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
