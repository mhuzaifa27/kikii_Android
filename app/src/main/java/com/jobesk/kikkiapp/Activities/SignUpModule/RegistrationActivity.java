package com.jobesk.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.jobesk.kikkiapp.R;
import com.jobesk.kikkiapp.Utils.ShowDialogues;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegistrationActivity";
    private Context mContext=RegistrationActivity.this;

    private Button btn_next;
    private LinearLayout ll_birthday;
    private TextView tv_birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initComponents();
        btn_next.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
    }

    private void initComponents() {
        btn_next=findViewById(R.id.btn_next);

        ll_birthday=findViewById(R.id.ll_birthday);

        tv_birthday=findViewById(R.id.tv_birthday);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                startActivity(new Intent(mContext,AddProfileImageActivity.class));
                break;
            case R.id.ll_birthday:
                ShowDialogues.SHOW_DATE_PICKER_DIALOG(this,tv_birthday);
                break;
        }
    }
}
