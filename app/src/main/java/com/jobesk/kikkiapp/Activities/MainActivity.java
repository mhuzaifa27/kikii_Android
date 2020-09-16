package com.jobesk.kikkiapp.Activities;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jobesk.kikkiapp.Events.CheckInternetEvent;
import com.jobesk.kikkiapp.Fragments.Main.ChatFragment;
import com.jobesk.kikkiapp.Fragments.Main.MatchFragment;
import com.jobesk.kikkiapp.Fragments.Main.MeetFragment;
import com.jobesk.kikkiapp.Fragments.Main.NotificationFragment;
import com.jobesk.kikkiapp.Fragments.Main.SocialFragment;
import com.jobesk.kikkiapp.R;
import com.jobesk.kikkiapp.Utils.CheckConnectivity;
import com.jobesk.kikkiapp.Utils.SessionManager;
import com.jobesk.kikkiapp.Utils.ShowDialogues;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniComponents();
        loadHomeFragment();
        setUpNavigationView();

    }

    private void iniComponents() {
        extras = getIntent().getStringExtra("CURRENT_TAG");;
        //checkIntentData();

        main_frame = findViewById(R.id.main_frame);
        mNetworkReceiver = new CheckConnectivity();
        fm = getSupportFragmentManager();
       /* mNoNet = new NoNet();
        mNoNet.initNoNet(this, fm);*/
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
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
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
                        CURRENT_TAG = TAG_MEET;
                        break;
                    case R.id.bottom_nav_match:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_MATCH;
                        break;
                    case R.id.bottom_nav_chat:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CHAT;
                        break;
                    case R.id.bottom_nav_social:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_SOCIAL;
                        break;
                    case R.id.bottom_nav_notify:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_NOTIFICATION;
                        break;
                    default:
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
        new AlertDialog.Builder(this,R.style.MyDialogTheme)
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
            ShowDialogues.SHOW_SNACK_BAR(parentLayout,MainActivity.this,getString(R.string.snackbar_internet_available));
            Log.d("fffff", "onMessageEvent: "+CURRENT_TAG);
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
            ShowDialogues.SHOW_SNACK_BAR(parentLayout,MainActivity.this,getString(R.string.snackbar_check_internet));
        }
    }

}

