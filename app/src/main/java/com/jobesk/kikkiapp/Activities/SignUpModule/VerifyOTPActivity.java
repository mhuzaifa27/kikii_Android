package com.jobesk.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jobesk.kikkiapp.Activities.MainActivity;
import com.jobesk.kikkiapp.Callbacks.CallbackSentOTP;
import com.jobesk.kikkiapp.Callbacks.CallbackVerifyOTP;
import com.jobesk.kikkiapp.Netwrok.API;
import com.jobesk.kikkiapp.Netwrok.Constant;
import com.jobesk.kikkiapp.Netwrok.RestAdapter;
import com.jobesk.kikkiapp.R;
import com.jobesk.kikkiapp.Utils.CustomLoader;
import com.jobesk.kikkiapp.Utils.SessionManager;
import com.jobesk.kikkiapp.Utils.ShowDialogues;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTPActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OTPActivity";
    private Context mContext = VerifyOTPActivity.this;
    private EditText et_otp_1, et_otp_2, et_otp_3, et_otp_4, et_otp_5, et_otp_6;
    private Button btn_next;

    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackVerifyOTP> callbackVerifyOTPCall;
    private CallbackVerifyOTP responseVerifyOTP;
    private String code;
    private Map<String, String> verifyOTPParams = new HashMap<>();


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
        customLoader = new CustomLoader(this, false);

        sessionManager = new SessionManager(this);

        et_otp_1 = findViewById(R.id.et_otp_1);
        et_otp_2 = findViewById(R.id.et_otp_2);
        et_otp_3 = findViewById(R.id.et_otp_3);
        et_otp_4 = findViewById(R.id.et_otp_4);
        et_otp_5 = findViewById(R.id.et_otp_5);
        et_otp_6 = findViewById(R.id.et_otp_6);

        btn_next = findViewById(R.id.btn_next);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (et_otp_1.getText().toString().isEmpty()) {
                    ShowError();
                } else if (et_otp_2.getText().toString().isEmpty()) {
                    ShowError();
                } else if (et_otp_3.getText().toString().isEmpty()) {
                    ShowError();
                } else if (et_otp_4.getText().toString().isEmpty()) {
                    ShowError();
                } else if (et_otp_5.getText().toString().isEmpty()) {
                    ShowError();
                } else if (et_otp_6.getText().toString().isEmpty()) {
                    ShowError();
                } else {
                    code =  et_otp_1.getText().toString() +
                            et_otp_2.getText().toString() +
                            et_otp_3.getText().toString() +
                            et_otp_4.getText().toString() +
                            et_otp_5.getText().toString() +
                            et_otp_6.getText().toString();
                    verifyOTPParams.put(Constant.CODE,code);
                    verifyOTP();
                }
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
            switch (view.getId()) {
                case R.id.et_otp_1:
                    if (text.length() == 1)
                        et_otp_2.requestFocus();
                    break;
                case R.id.et_otp_2:
                    if (text.length() == 1)
                        et_otp_3.requestFocus();
                    else if (text.length() == 0)
                        et_otp_1.requestFocus();
                    break;
                case R.id.et_otp_3:
                    if (text.length() == 1)
                        et_otp_4.requestFocus();
                    else if (text.length() == 0)
                        et_otp_2.requestFocus();
                    break;
                case R.id.et_otp_4:
                    if (text.length() == 1)
                        et_otp_5.requestFocus();
                    else if (text.length() == 0)
                        et_otp_3.requestFocus();
                    break;
                case R.id.et_otp_5:
                    if (text.length() == 1)
                        et_otp_6.requestFocus();
                    else if (text.length() == 0)
                        et_otp_4.requestFocus();
                    break;
                case R.id.et_otp_6:
                    if (text.length() == 0)
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

    public void verifyOTP() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(mContext);
        Log.d(TAG, "sendOTP: " + code);
        callbackVerifyOTPCall = api.verifyOTP(sessionManager.getAccessToken(), verifyOTPParams);
        callbackVerifyOTPCall.enqueue(new Callback<CallbackVerifyOTP>() {
            @Override
            public void onResponse(Call<CallbackVerifyOTP> call, Response<CallbackVerifyOTP> response) {
                Log.d(TAG, "onResponse: " + response);
                responseVerifyOTP = response.body();
                if (responseVerifyOTP != null) {
                    if (responseVerifyOTP.getSuccess()) {
                        Log.d(TAG, "onResponse: " + responseVerifyOTP.getMessage());
                        String email=responseVerifyOTP.getData().getUser().getEmail();
                        customLoader.hideIndicator();
                        Toast.makeText(mContext, responseVerifyOTP.getMessage(), Toast.LENGTH_SHORT).show();
                        if(email!=null){
                            startActivity(new Intent(mContext, MainActivity.class));
                            finish();
                        }
                        else{
                            startActivity(new Intent(mContext, LocationActivity.class));
                            finish();
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + responseVerifyOTP.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(mContext, responseVerifyOTP.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(mContext);
                }
            }

            @Override
            public void onFailure(Call<CallbackVerifyOTP> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void ShowError() {
        Toast.makeText(mContext, "Please enter complete OTP", Toast.LENGTH_SHORT).show();
    }
}
