package com.jobesk.kikkiapp.Activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jobesk.kikkiapp.R;

public class AddBioActivity extends AppCompatActivity {

    private static final String TAG = "AddBioActivity";
    private Context context=AddBioActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bio);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,RelationshipStatusActivity.class));
            }
        });
    }
}
