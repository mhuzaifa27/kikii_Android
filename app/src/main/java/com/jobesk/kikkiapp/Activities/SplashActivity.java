package com.jobesk.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.jobesk.kikkiapp.Activities.SignUpModule.SignUpActivity;
import com.jobesk.kikkiapp.R;
import com.jobesk.kikkiapp.Utils.SessionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager=new SessionManager(this);

        printKeyHash();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!sessionManager.checkLogin()){
                    if(sessionManager.getFirstAttempt()){
                        startActivity(new Intent(SplashActivity.this,SignUpActivity.class));
                    }
                    else{
                        sessionManager.saveFirstAttempt(false);
                        startActivity(new Intent(SplashActivity.this,OnBoardingActivity.class));
                    }
                }
                else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        },3000);
    }

    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("keyhash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
