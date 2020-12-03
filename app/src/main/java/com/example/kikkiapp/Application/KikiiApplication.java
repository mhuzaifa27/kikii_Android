package com.example.kikkiapp.Application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.example.kikkiapp.BuildConfig;
import com.example.kikkiapp.Firebase.AppState;
import com.example.kikkiapp.Firebase.ChangeEventListener;
import com.example.kikkiapp.Firebase.Services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;


/**
 * Created by thanh on 09/12/2015.
 */
public class KikiiApplication extends Application {
    Context mContext;

    private static final String TAG = "KikiiApplication";
    public static KikiiApplication kikiiApplication;

    public static KikiiApplication getKikiiApplication() {
        return kikiiApplication;
    }

    public Locale mLocale;
    public SimpleDateFormat dayFormat;
    public SimpleDateFormat weatherDateStampFormat;

    private DatabaseReference mDatabase;
    public boolean changeSubCategoryComplete = false;
    private UserService userService;


    public void onCreate() {
        super.onCreate();
        mContext = KikiiApplication.this;
        kikiiApplication = this;

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            AppState.currentFireUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        userService = new UserService();
        userService.setOnChangedListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(EventType type, int index, int oldIndex) {
                userService.isLoaded = true;
            }

            @Override
            public void onDataChanged() {
                userService.isLoaded = true;
                if(userService.getCount() > 0 && AppState.currentFireUser != null) {
                    AppState.currentBpackCustomer = userService.getUserById(AppState.currentFireUser.getUid());
                    if (AppState.currentBpackCustomer != null) {
                        Log.d(TAG, "USER FOUND: " + AppState.currentFireUser.getUid());
                    } else {
                        Log.d(TAG, "USER NOT FOUND: " + AppState.currentFireUser.getUid());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        mLocale = getResources().getConfiguration().locale;
        dayFormat = new SimpleDateFormat("EEE", mLocale);
        weatherDateStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", mLocale);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        clearMemoryCache();
    }

    public void clearMemoryCache(){

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        clearMemoryCache();
    }

    private void setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());
        }
    }
}
