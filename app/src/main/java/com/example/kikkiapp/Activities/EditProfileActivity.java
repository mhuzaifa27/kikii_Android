package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kikkiapp.Activities.Profile.AddBioActivity;
import com.example.kikkiapp.Activities.Profile.GenderIdentityActivity;
import com.example.kikkiapp.Activities.Profile.PronounsActivity;
import com.example.kikkiapp.Activities.Profile.SexualIdentityActivity;
import com.example.kikkiapp.R;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";
    private Context context=EditProfileActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        findViewById(R.id.ll_gender_identity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, GenderIdentityActivity.class));
            }
        });
        findViewById(R.id.ll_sexual_identity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SexualIdentityActivity.class));
            }
        });
        findViewById(R.id.ll_pronouns).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PronounsActivity.class));
            }
        });
        findViewById(R.id.ll_bio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddBioActivity.class));
            }
        });

    }
}
