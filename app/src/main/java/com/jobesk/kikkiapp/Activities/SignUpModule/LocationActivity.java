package com.jobesk.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jobesk.kikkiapp.R;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LocationActivity";
    private Context mContext= LocationActivity.this;

    private Button btn_enable_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initComponents();
        btn_enable_location.setOnClickListener(this);
    }

    private void initComponents() {
        btn_enable_location=findViewById(R.id.btn_enable_location);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_enable_location:
                startActivity(new Intent(mContext,RegistrationActivity.class));
                break;
        }
    }
}
