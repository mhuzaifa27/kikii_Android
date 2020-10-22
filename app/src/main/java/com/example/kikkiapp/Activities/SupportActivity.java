package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CommonMethods;
import com.example.kikkiapp.Utils.SessionManager;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SupportActivity";
    private Context context=SupportActivity.this;
    private Activity activity=SupportActivity.this;

    private ImageView img_back;
    private TextView tv_logout;
    private SessionManager sessionManager;

    private LinearLayout ll_terms_and_condition,ll_privacy_policy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        initComponents();

        img_back.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        ll_terms_and_condition.setOnClickListener(this);
        ll_privacy_policy.setOnClickListener(this);

    }

    private void initComponents() {
        sessionManager=new SessionManager(this);

        img_back=findViewById(R.id.img_back);

        tv_logout=findViewById(R.id.tv_logout);

        ll_privacy_policy=findViewById(R.id.ll_privacy_policy);
        ll_terms_and_condition=findViewById(R.id.ll_terms_and_condition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                CommonMethods.goBack(this);
                break;
            case R.id.tv_logout:
                sessionManager.logoutUser();
                finish();
                break;
            case R.id.ll_terms_and_condition:
                startActivity(new Intent(context,TermsAndConditionsActivity.class));
                break;
            case R.id.ll_privacy_policy:
                startActivity(new Intent(context,PrivacyPolicyActivity.class));
                break;
        }
    }
}
