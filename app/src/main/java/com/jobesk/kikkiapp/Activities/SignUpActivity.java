package com.jobesk.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jobesk.kikkiapp.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";
    private Context mContext=SignUpActivity.this;
    private Button btn_cont_with_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponents();
        btn_cont_with_phone.setOnClickListener(this);
    }

    private void initComponents() {
        btn_cont_with_phone=findViewById(R.id.btn_cont_with_phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cont_with_phone:
                startActivity(new Intent(mContext,EnterPhoneNumberActivity.class));
        }
    }
}
