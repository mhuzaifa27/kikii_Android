package com.example.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kikkiapp.Activities.MainActivity;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Callbacks.CallbackVerifyOTP;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    private Call<CallbackStatus> callbackStatusCall;
    private CallbackStatus responseResendCode;

    private String code;
    private Map<String, String> verifyOTPParams = new HashMap<>();
    private LinearLayout ll_timer,ll_resend;
    private TextView tv_time,tv_resend_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        initComponents();
        startCountDown();

        et_otp_1.addTextChangedListener(new GenericTextWatcher(et_otp_1));
        et_otp_2.addTextChangedListener(new GenericTextWatcher(et_otp_2));
        et_otp_3.addTextChangedListener(new GenericTextWatcher(et_otp_3));
        et_otp_4.addTextChangedListener(new GenericTextWatcher(et_otp_4));
        et_otp_5.addTextChangedListener(new GenericTextWatcher(et_otp_5));
        et_otp_6.addTextChangedListener(new GenericTextWatcher(et_otp_6));

        btn_next.setOnClickListener(this);
        tv_resend_code.setOnClickListener(this);
    }

    private void startCountDown() {
        ll_resend.setVisibility(View.GONE);
        ll_timer.setVisibility(View.VISIBLE);
        new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                tv_time.setText(text);
            }
            @Override
            public void onFinish() {
                ll_resend.setVisibility(View.VISIBLE);
                ll_timer.setVisibility(View.GONE);
            }
        }.start();
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

        ll_resend=findViewById(R.id.ll_resend);
        ll_timer=findViewById(R.id.ll_timer);

        tv_resend_code=findViewById(R.id.tv_resend_code);
        tv_time=findViewById(R.id.tv_time);
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
            case R.id.tv_resend_code:
                resendCode();
                break;
        }
    }

    private void resendCode() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(mContext);
        Log.d(TAG, "sendOTP: " + sessionManager.getAccessToken());
        callbackStatusCall = api.resendOTP(sessionManager.getAccessToken());
        callbackStatusCall.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                responseResendCode = response.body();
                if (responseResendCode != null) {
                    customLoader.hideIndicator();
                    if (responseResendCode.getSuccess()) {
                        Log.d(TAG, "onResponse: " + responseResendCode.getMessage());
                        Toast.makeText(mContext, responseResendCode.getMessage(), Toast.LENGTH_SHORT).show();
                        startCountDown();
                    } else {
                        Log.d(TAG, "onResponse: " + responseResendCode.getMessage());
                        Toast.makeText(mContext, responseResendCode.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(mContext);
                }
            }

            @Override
            public void onFailure(Call<CallbackStatus> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
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
                            sessionManager.saveUserEmail(responseVerifyOTP.getData().getUser().getEmail());
                            sessionManager.saveUserName(responseVerifyOTP.getData().getUser().getName());
                            sessionManager.saveBirthday(responseVerifyOTP.getData().getUser().getBirthday());
                            sessionManager.saveUserID(responseVerifyOTP.getData().getUser().getId().toString());
                            sessionManager.createLoginSession("Bearer "+responseVerifyOTP.getData().getUser().getAuthToken(),responseVerifyOTP.getData().getUser().getId().toString());
                            Toast.makeText(mContext, responseVerifyOTP.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(mContext, MainActivity.class);
                            TaskStackBuilder.create(mContext).addNextIntentWithParentStack(loginIntent).startActivities();
                            verifyOTPParams.clear();
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
