package com.jobesk.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jobesk.kikkiapp.R;

public class MyProfileActivity extends AppCompatActivity {

    private static final String TAG = "MyProfileActivity";
    private Context context=MyProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        findViewById(R.id.img_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,EditProfileActivity.class));
            }
        });
    }
}
