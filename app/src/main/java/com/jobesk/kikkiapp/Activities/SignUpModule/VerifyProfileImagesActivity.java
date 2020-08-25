package com.jobesk.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jobesk.kikkiapp.R;

import java.nio.file.attribute.GroupPrincipal;

public class VerifyProfileImagesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VerifyProfileImagesActi";
    private Context mContext=VerifyProfileImagesActivity.this;

    private ImageView img_verify_complete,img_face_detection;
    private Button btn_verify,btn_decline,btn_next;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_profile_images);

        initComponents();

        btn_decline.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        btn_next.setOnClickListener(this);


    }

    private void initComponents() {
        img_verify_complete=findViewById(R.id.img_verify_complete);
        img_face_detection=findViewById(R.id.img_face_detection);

        btn_decline=findViewById(R.id.btn_decline);
        btn_verify=findViewById(R.id.btn_verify);
        btn_next=findViewById(R.id.btn_next);

        tv_title=findViewById(R.id.tv_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_decline:
                break;
            case R.id.btn_verify:
                setVerifiedView();
                break;
            case R.id.btn_next:
                startActivity(new Intent(mContext,AgreementActivity.class));
                break;
        }
    }

    private void setVerifiedView() {
        img_verify_complete.setVisibility(View.VISIBLE);
        img_face_detection.setVisibility(View.GONE);

        btn_decline.setVisibility(View.GONE);
        btn_verify.setVisibility(View.GONE);
        btn_next.setVisibility(View.VISIBLE);

        tv_title.setText("Verification Success");
    }
}
