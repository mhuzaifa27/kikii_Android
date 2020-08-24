package com.jobesk.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jobesk.kikkiapp.R;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OTPActivity";
    private Context mContext=OTPActivity.this;
    private EditText et_otp_1, et_otp_2, et_otp_3, et_otp_4, et_otp_5, et_otp_6;

    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initComponents();

        et_otp_1.addTextChangedListener(new GenericTextWatcher(et_otp_1));
        et_otp_2.addTextChangedListener(new GenericTextWatcher(et_otp_2));
        et_otp_3.addTextChangedListener(new GenericTextWatcher(et_otp_3));
        et_otp_4.addTextChangedListener(new GenericTextWatcher(et_otp_4));
        et_otp_5.addTextChangedListener(new GenericTextWatcher(et_otp_5));
        et_otp_6.addTextChangedListener(new GenericTextWatcher(et_otp_6));

        btn_next.setOnClickListener(this);
    }

    private void initComponents() {
        et_otp_1 =findViewById(R.id.et_otp_1);
        et_otp_2 =findViewById(R.id.et_otp_2);
        et_otp_3 =findViewById(R.id.et_otp_3);
        et_otp_4 =findViewById(R.id.et_otp_4);
        et_otp_5 =findViewById(R.id.et_otp_5);
        et_otp_6 =findViewById(R.id.et_otp_6);

        btn_next=findViewById(R.id.btn_next);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                startActivity(new Intent(mContext,LocationActivity.class));
                break;
        }
    }

    public class GenericTextWatcher implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId()) {
                case R.id.et_otp_1:
                    if(text.length()==1)
                        et_otp_2.requestFocus();
                    break;
                case R.id.et_otp_2:
                    if(text.length()==1)
                        et_otp_3.requestFocus();
                    else if(text.length()==0)
                        et_otp_1.requestFocus();
                    break;
                case R.id.et_otp_3:
                    if(text.length()==1)
                        et_otp_4.requestFocus();
                    else if(text.length()==0)
                        et_otp_2.requestFocus();
                    break;
                case R.id.et_otp_4:
                    if(text.length()==1)
                        et_otp_5.requestFocus();
                    else if(text.length()==0)
                        et_otp_3.requestFocus();
                    break;
                case R.id.et_otp_5:
                    if(text.length()==1)
                        et_otp_6.requestFocus();
                    else if(text.length()==0)
                        et_otp_4.requestFocus();
                    break;
                case R.id.et_otp_6:
                    if(text.length()==0)
                        et_otp_5.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }
}
