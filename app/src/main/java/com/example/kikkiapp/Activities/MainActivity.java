package com.example.kikkiapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.kikkiapp.Callbacks.CallbackUpdateProfile;
import com.example.kikkiapp.Firebase.AppState;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constants;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.kikkiapp.Events.CheckInternetEvent;
import com.example.kikkiapp.Fragments.Main.ChatFragment;
import com.example.kikkiapp.Fragments.Main.MatchFragment;
import com.example.kikkiapp.Fragments.Main.MeetFragment;
import com.example.kikkiapp.Fragments.Main.NotificationFragment;
import com.example.kikkiapp.Fragments.Main.SocialFragment;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CheckConnectivity;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity.this;
    BottomNavigationView bottomNavigationView;
    FrameLayout main_frame;

    MeetFragment meetFragment;
    MatchFragment matchFragment;
    ChatFragment chatFragment;
    SocialFragment socialFragment;
    NotificationFragment notificationFragment;

    private BroadcastReceiver mNetworkReceiver;
    private FragmentManager fm = null;
//    private static NoNet mNoNet;

    public static final String TAG_MEET = "tag_meet";
    public static final String TAG_MATCH = "tag_match";
    public static final String TAG_CHAT = "tag_chat";
    public static final String TAG_SOCIAL = "tag_social";
    public static final String TAG_NOTIFICATION = "tag_notification";
    public static String CURRENT_TAG = TAG_MEET;
    public static int navItemIndex = 0;
    private Handler mHandler;
    private SessionManager sessionManager;
    private EventBus eventBus = EventBus.getDefault();

    int count = 0;
    private boolean shouldLoadHomeFragOnBackPress = true;

    View parentLayout;
    String extras;


    /******/
    private GoogleApiClient googleApiClient;
    private Location mylocation;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Call<CallbackUpdateProfile> callbackStatusCall;
    private CallbackUpdateProfile responseLatLongUpdate;
    private Map<String, String> updateLocationParams = new HashMap<>();
    private Double latitude, longitude;
    private String post_id, event_id, user_id;

    /*****/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpGClient();
        checkPermissions();
        iniComponents();
        loadHomeFragment();
        setUpNavigationView();

        /******/
        OnlineUser();
        /******/

        checkForDeepLink();
    }

    private void checkForDeepLink() {
        //////////// Receiving Deep Link
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            post_id = deepLink.getQueryParameter("post_id");
                            Log.d(TAG, "onSuccess: " + post_id);
                            if (post_id != null) {
                                Intent intent = new Intent(mContext, SinglePostDetailActivity.class);
                                intent.putExtra(Constants.ID, String.valueOf(post_id));
                                startActivity(intent);
                            } else {
                                event_id = deepLink.getQueryParameter("event_id");
                                if (event_id != null) {

                                } else {
                                    user_id = deepLink.getQueryParameter("user_id");
                                    if (user_id != null) {
                                        Intent intent = new Intent(mContext, UserProfileActivity.class);
                                        intent.putExtra(Constants.ID, String.valueOf(user_id));
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("dynamic link tag", "getDynamicLink:onFailure", e);
                    }
                });

    }

    private void iniComponents() {
        extras = getIntent().getStringExtra("CURRENT_TAG");
        ;
        //checkIntentData();

        main_frame = findViewById(R.id.main_frame);
        mNetworkReceiver = new CheckConnectivity();
        fm = getSupportFragmentManager();
        mHandler = new Handler();
        eventBus.register(this);
        registerNetworkBroadcastForNougat();

        meetFragment = new MeetFragment();
        matchFragment = new MatchFragment();
        chatFragment = new ChatFragment();
        socialFragment = new SocialFragment();
        notificationFragment = new NotificationFragment();
        sessionManager = new SessionManager(this);

        bottomNavigationView = findViewById(R.id.main_bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_meet);
        parentLayout = findViewById(android.R.id.content);
    }

    /*private void checkIntentData() {
        if(extras != null ){
            if (extras.equals("tag_live")) {
                navItemIndex=2;
                CURRENT_TAG=TAG_LIVE;
            }
            else if (extras.equals("tag_settings")) {
                navItemIndex=4;
                CURRENT_TAG=TAG_SETTINGS;
            }
            else if (extras.equals("tag_search")) {
                navItemIndex=1;
                CURRENT_TAG=TAG_SEARCH;
            }
            else if (extras.equals("tag_alert")) {
                navItemIndex=3;
                CURRENT_TAG=TAG_ALERT;

            }
        }
        else{
            navItemIndex=0;
            CURRENT_TAG=TAG_HOME;
        }
    }*/

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
        unregisterNetworkChanges();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadHomeFragment() {

        selectNavMenu();
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getMeetFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
               /* fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);*/
                fragmentTransaction.replace(R.id.main_frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        invalidateOptionsMenu();
    }

    private void selectNavMenu() {
        bottomNavigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private Fragment getMeetFragment() {
        switch (navItemIndex) {
            case 0:
                return meetFragment;
            case 1:
                return matchFragment;
            case 2:
                return chatFragment;
            case 3:
                return socialFragment;
            case 4:
                return notificationFragment;
            default:
                return new MeetFragment();
        }
    }

    private void setUpNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_nav_meet:
                        navItemIndex = 0;
                        SocialFragment.navItemIndex = 0;
                        CURRENT_TAG = TAG_MEET;
                        break;
                    case R.id.bottom_nav_match:
                        navItemIndex = 1;
                        SocialFragment.navItemIndex = 0;
                        CURRENT_TAG = TAG_MATCH;
                        break;
                    case R.id.bottom_nav_chat:
                        navItemIndex = 2;
                        SocialFragment.navItemIndex = 0;
                        CURRENT_TAG = TAG_CHAT;
                        break;
                    case R.id.bottom_nav_social:
                        navItemIndex = 3;
                        SocialFragment.navItemIndex = 0;
                        CURRENT_TAG = TAG_SOCIAL;
                        break;
                    case R.id.bottom_nav_notify:
                        navItemIndex = 4;
                        SocialFragment.navItemIndex = 0;
                        CURRENT_TAG = TAG_NOTIFICATION;
                        break;
                    default:
                        SocialFragment.navItemIndex = 0;
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_MEET;
                loadHomeFragment();
                return;
            }
        }
        String catfrag = sessionManager.getfragmentval("catfrag");
        if (catfrag.equals("10")) {
            sessionManager.setfragmentval("catfrag", "0");
            navItemIndex = 0;
            CURRENT_TAG = TAG_MEET;
            loadHomeFragment();
            return;
        }
        clickDone();
    }

    public void clickDone() {
        new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(R.string.close_warning)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    //    /**
//     * EVENTS
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(NotificationReceivedEvent event) {
//        if (event.isIS_NOTIFY()) {
//            Snackbar snackbar = Snackbar.make(parentLayout, event.getRemoteMessage().getNotification().getTitle(), Snackbar.LENGTH_LONG);
//            View customView = getLayoutInflater().inflate(R.layout.snackbar_custom_notify, null);
//            //snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
//            Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//            snackbarLayout.setPadding(0, 0, 0, 0);
//            TextView tv_subject = customView.findViewById(R.id.tv_subject);
//            TextView tv_body = customView.findViewById(R.id.tv_body);
//
//            tv_subject.setText(event.getRemoteMessage().getNotification().getTitle());
//            tv_body.setText(event.getRemoteMessage().getNotification().getBody());
//
//            snackbarLayout.addView(customView);
//            snackbar.show();
//        }
//    }
//
//    ;
//
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CheckInternetEvent event) {
        Log.d("SsS", "checkInternetAvailability: called");
        if (event.isIS_INTERNET_AVAILABLE()) {
            ShowDialogues.SHOW_SNACK_BAR(parentLayout, MainActivity.this, getString(R.string.snackbar_internet_available));
            Log.d("fffff", "onMessageEvent: " + CURRENT_TAG);
            switch (CURRENT_TAG) {
                case TAG_MEET:
                    /*HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_HOME);
                    homeFragment.loadUserProfileData();*/
                case TAG_MATCH:
                    /*AlertFragment alertFragment = (AlertFragment) getSupportFragmentManager().findFragmentByTag(TAG_ALERT);
                    alertFragment.getAllNotifications();*/
                case TAG_CHAT:
                   /* StartBroadcastFragment startBroadcastFragment = (StartBroadcastFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIVE);
                    startBroadcastFragment.loadFollowingStreams();*/
                case TAG_SOCIAL:
                    /*SettingsFragment settingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(TAG_SETTINGS);
//                    settingsFragment.getSettingsData();*/
            }
        } else {
            ShowDialogues.SHOW_SNACK_BAR(parentLayout, MainActivity.this, getString(R.string.snackbar_check_internet));
        }
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions((Activity) mContext,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            //customLoader.showIndicator();
            getMyLocation();
        }
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, MainActivity.this);
                    PendingResult result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback() {

                        @Override
                        public void onResult(@NonNull Result result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(mContext,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);

                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult((Activity) mContext,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. However, we have no way to fix the
                                    // settings so we won't show the dialog.
                                    //finish();
                                    break;
                            }
                        }


                    });
                }
            }
        }
    }

    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage((FragmentActivity) mContext, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //customLoader.showIndicator();
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                }
                break;
        }
        int permissionLocation = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            mylocation = location;
            if (mylocation != null) {
                latitude = mylocation.getLatitude();
                longitude = mylocation.getLongitude();

                sessionManager.saveLat(String.valueOf(latitude));
                sessionManager.saveLng(String.valueOf(longitude));

                //updateLocation();
                Log.d("TAG", "onLocationChanged:LATITUDE " + latitude);
                Log.d("TAG", "onLocationChanged:LONGITUDE " + longitude);

                updateLocationParams.put(Constants.LATITUDE, sessionManager.getLat());
                updateLocationParams.put(Constants.LONGITUDE, sessionManager.getLng());
                updateLocation();
            }
        } catch (Exception e) {
            Log.d("TAG", "onLocationChanged: " + e.getMessage());
        }
    }

    public void updateLocation() {
        API api = RestAdapter.createAPI(mContext);
        Log.d(TAG, "sendOTP: " + updateLocationParams.size());
        Log.d(TAG, "token: " + sessionManager.getAccessToken());
        callbackStatusCall = api.updateProfile(sessionManager.getAccessToken(), updateLocationParams);
        callbackStatusCall.enqueue(new Callback<CallbackUpdateProfile>() {
            @Override
            public void onResponse(Call<CallbackUpdateProfile> call, Response<CallbackUpdateProfile> response) {
                Log.d(TAG, "onResponse: " + response);
                responseLatLongUpdate = response.body();
                if (responseLatLongUpdate != null) {
                    if (responseLatLongUpdate.getSuccess()) {
                        Log.d(TAG, "onResponse: " + responseLatLongUpdate.getMessage());

                        //customLoader.hideIndicator();
                        //goToNextActivity();
                    } else {
                        Log.d(TAG, "onResponse: " + responseLatLongUpdate.getMessage());
                        //customLoader.hideIndicator();
                        Toast.makeText(mContext, responseLatLongUpdate.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(mContext);
                }
            }

            @Override
            public void onFailure(Call<CallbackUpdateProfile> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    //customLoader.hideIndicator();
                }
            }
        });
    }

    private void OnlineUser() {
        DatabaseReference mUsersDatabase = null;
        if (AppState.currentBpackCustomer != null) {
            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(AppState.currentFireUser.getUid());

            DatabaseReference finalMUsersDatabase = mUsersDatabase;
            mUsersDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        finalMUsersDatabase.child("isOnline").onDisconnect()
                                .setValue("false");
                        finalMUsersDatabase.child("isOnline").setValue("true");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }
}

