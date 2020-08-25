package com.jobesk.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jobesk.kikkiapp.R;

public class EnterPhoneNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EnterPhoneNumberActivit";
    private Context mContext=EnterPhoneNumberActivity.this;

    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_number);

        initComponents();
        btn_next.setOnClickListener(this);
    }

    private void initComponents() {
        btn_next=findViewById(R.id.btn_next);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                startActivity(new Intent(mContext, OTPActivity.class));
                break;
        }
    }
}
