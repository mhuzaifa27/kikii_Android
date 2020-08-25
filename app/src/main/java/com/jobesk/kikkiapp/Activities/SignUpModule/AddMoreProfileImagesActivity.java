package com.jobesk.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jobesk.kikkiapp.R;

public class AddMoreProfileImagesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddMoreProfileImagesAct";
    private Context mContext=AddMoreProfileImagesActivity.this;

    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more_profile_images);

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
                startActivity(new Intent(mContext,VerifyProfileImagesActivity.class));
                break;
        }
    }
}
