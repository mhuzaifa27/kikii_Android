package com.example.kikkiapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.kikkiapp.Netwrok.Constants;
import com.example.kikkiapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class HandelDeepLinkActivity extends AppCompatActivity {

    private static final String TAG = "HandelDeepLinkActivity";
    private String post_id, event_id, user_id;
    private Context context=HandelDeepLinkActivity.this;
    private Activity activity=HandelDeepLinkActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handel_deep_link);

    }
}