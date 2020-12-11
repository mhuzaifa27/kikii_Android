package com.example.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kikkiapp.Callbacks.CallbackUpdateProfile;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constants;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.example.kikkiapp.Utils.UtilityFunctions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegistrationActivity";
    private Context mContext=RegistrationActivity.this;

    private Button btn_next;
    private LinearLayout ll_birthday;
    private TextView tv_birthday,tv_age;
    private EditText et_name,et_email;
    private Map<String ,String> registerParams=new HashMap<>();
    private CustomLoader customLoader;
    private SessionManager sessionManager;
    private Call<CallbackUpdateProfile> callbackStatusCall;
    private CallbackUpdateProfile responseRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initComponents();
        setData();

        btn_next.setOnClickListener(this);
        ll_birthday.setOnClickListener(this);
    }

    private void setData() {
        if(sessionManager.getFbLogin()){
            if(sessionManager.getUserName()!=null || !sessionManager.getUserName().equalsIgnoreCase("")){
                et_name.setText(sessionManager.getUserName());
                et_name.setEnabled(false);
            }
            if(sessionManager.getUserEmail()!=null || !sessionManager.getUserEmail().equalsIgnoreCase("")){
                et_email.setText(sessionManager.getUserEmail());
                et_email.setEnabled(false);
            }
        }
        else if(sessionManager.getInstaLogin()){
            if(sessionManager.getUserName()!=null || !sessionManager.getUserName().equalsIgnoreCase("")){
                et_name.setText(sessionManager.getUserName());
                et_name.setEnabled(false);
            }
        }
    }

    private void initComponents() {
        customLoader=new CustomLoader(this,false);
        sessionManager=new SessionManager(this);

        btn_next=findViewById(R.id.btn_next);

        ll_birthday=findViewById(R.id.ll_birthday);

        tv_birthday=findViewById(R.id.tv_birthday);
        tv_age=findViewById(R.id.tv_age);

        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                if(et_name.getText().toString().isEmpty()){
                    et_name.setError(getResources().getString(R.string.et_error));
                }
                else if(et_email.getText().toString().isEmpty()){
                    et_email.setError(getResources().getString(R.string.et_error));
                }
                else if(tv_birthday.getText().toString().isEmpty()){
                    Toast.makeText(mContext, "Select Birth Date!", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerParams.put(Constants.NAME,et_name.getText().toString());
                    registerParams.put(Constants.EMAIL,et_email.getText().toString());
                    registerParams.put(Constants.BIRTHDAY,tv_birthday.getText().toString());
                    register();
                }
                //startActivity(new Intent(mContext,AddProfileImageActivity.class));
                break;
            case R.id.ll_birthday:
                ShowDialogues.SHOW_DATE_PICKER_DIALOG(this, tv_birthday, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        String birthdateStr = tv_birthday.getText().toString();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date birthdate = null;
                        try {
                            birthdate = df.parse(birthdateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tv_age.setText(UtilityFunctions.calculateAge(birthdate)+" Years");
                    }
                });
                break;
        }
    }

    public void register() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(mContext);
        Log.d(TAG, "sendOTP: "+registerParams.size());
        Log.d(TAG, "token: "+sessionManager.getAccessToken());
        callbackStatusCall = api.register(sessionManager.getAccessToken(),registerParams);
        callbackStatusCall.enqueue(new Callback<CallbackUpdateProfile>() {
            @Override
            public void onResponse(Call<CallbackUpdateProfile> call, Response<CallbackUpdateProfile> response) {
                Log.d(TAG, "onResponse: " + response);
                responseRegister = response.body();
                if(responseRegister != null){
                    if (responseRegister.getSuccess()) {
                        Log.d(TAG, "onResponse: "+responseRegister.getMessage());
                        goToNextActivity();
                    } else {
                        Log.d(TAG, "onResponse: "+responseRegister.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(mContext, responseRegister.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(mContext);
                }
            }
            @Override
            public void onFailure(Call<CallbackUpdateProfile> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void goToNextActivity() {
        customLoader.hideIndicator();
        sessionManager.saveUserEmail(et_email.getText().toString());
        sessionManager.saveUserName(et_name.getText().toString());
        sessionManager.saveBirthday(tv_birthday.getText().toString());
        sessionManager.saveUserID(responseRegister.getUser().getId().toString());
        sessionManager.saveAccessToken("Bearer "+responseRegister.getUser().getAuthToken());
        sessionManager.createLoginSession("Bearer "+responseRegister.getUser().getAuthToken(),responseRegister.getUser().getId().toString());
        Toast.makeText(mContext, responseRegister.getMessage(), Toast.LENGTH_SHORT).show();
        Intent loginIntent = new Intent(mContext, AddProfileImageActivity.class);
        TaskStackBuilder.create(mContext).addNextIntentWithParentStack(loginIntent).startActivities();
        registerParams.clear();
    }
}
