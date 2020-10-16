package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CommonMethods;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SupportActivity";
    private Context context=SupportActivity.this;
    private Activity activity=SupportActivity.this;

    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        initComponents();

        img_back.setOnClickListener(this);
    }

    private void initComponents() {
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
