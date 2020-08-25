package com.jobesk.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jobesk.kikkiapp.R;
import com.jobesk.kikkiapp.Utils.ShowSelectImageBottomSheet;

public class AddProfileImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddProfileImageActivity";
    private Context mContext=AddProfileImageActivity.this;

    private Button btn_add_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_image);

        initComponents();

        btn_add_image.setOnClickListener(this);
    }

    private void initComponents() {
        btn_add_image=findViewById(R.id.btn_add_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_image:
                ShowSelectImageBottomSheet.showDialog(mContext,getWindow().getDecorView().getRootView());
                break;
        }
    }
}
