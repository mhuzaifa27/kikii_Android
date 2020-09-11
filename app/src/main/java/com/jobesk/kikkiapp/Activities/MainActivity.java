package com.jobesk.kikkiapp.Activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jobesk.kikkiapp.Fragments.Main.ChatFragment;
import com.jobesk.kikkiapp.Fragments.Main.MatchFragment;
import com.jobesk.kikkiapp.Fragments.Main.MeetFragment;
import com.jobesk.kikkiapp.Fragments.Main.NotificationFragment;
import com.jobesk.kikkiapp.Fragments.Main.SocialFragment;
import com.jobesk.kikkiapp.R;
import com.jobesk.kikkiapp.Utils.SessionManager;

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


    int count = 0;
    private boolean shouldLoadHomeFragOnBackPress = true;

    View parentLayout;
    String extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        extras = getIntent().getStringExtra("CURRENT_TAG");;
        //checkIntentData();

        main_frame = findViewById(R.id.main_frame);
//        mNetworkReceiver = new CheckConnectivity();
        fm = getSupportFragmentManager();
       /* mNoNet = new NoNet();
        mNoNet.initNoNet(this, fm);*/
        mHandler = new Handler();
//        eventBus.register(this);
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

        loadHomeFragment();
        setUpNavigationView();

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
//        eventBus.unregister(this);
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
        super.onBackPressed();
        //clickDone();
        finish();
    }

//    public void clickDone() {
//        new AlertDialog.Builder(this,R.style.MyDialogTheme)
//                .setIcon(R.mipmap.ic_launcher)
//                .setTitle(getResources().getString(R.string.app_name))
//                .setMessage(R.string.close_warning)
//                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        /*Intent i = new Intent();
//                        i.setAction(Intent.ACTION_MAIN);
//                        i.addCategory(Intent.CATEGORY_HOME);*/
//                        finish();
//                    }
//                })
//                .setNegativeButton("no", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .show();
//    }

//    private void showServerErrorDialog() {
//        Dialog dialog = new Dialog(MainActivity.this);
//        dialog.setContentView(R.layout.dialog_server_error);
//        Window window = dialog.getWindow();
//        window.setGravity(Gravity.CENTER);
//        window.getAttributes().windowAnimations = R.style.DialogAnimation;
//        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        TextView tv_btn_cancel = dialog.findViewById(R.id.tv_btn_cancel);
//
//        tv_btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

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
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(CheckInternetEvent event) {
//        Log.d("SsS", "checkInternetAvailability: called");
//        if (event.isIS_INTERNET_AVAILABLE()) {
//            Snackbar snackbar = Snackbar.make(parentLayout, "Internet connected!", Snackbar.LENGTH_LONG);
//            View customView = getLayoutInflater().inflate(R.layout.snackbar_internet_connection, null);
//            //snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
//            Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//            snackbarLayout.setPadding(0, 0, 0, 0);
//            TextView tv_subject = customView.findViewById(R.id.tv_subject);
//            tv_subject.setText("Internet connected!");
//
//            Log.e("--------id",sessionManager.getUserID());
//
//
//            snackbarLayout.addView(customView);
//            snackbar.show();
//            Log.d("fffff", "onMessageEvent: "+CURRENT_TAG);
//            switch (CURRENT_TAG) {
//                case TAG_HOME:
//                    HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_HOME);
//                    homeFragment.loadUserProfileData();
//                case TAG_ALERT:
//                    AlertFragment alertFragment = (AlertFragment) getSupportFragmentManager().findFragmentByTag(TAG_ALERT);
//                    alertFragment.getAllNotifications();
//                case TAG_LIVE:
//                    StartBroadcastFragment startBroadcastFragment = (StartBroadcastFragment) getSupportFragmentManager().findFragmentByTag(TAG_LIVE);
//                    startBroadcastFragment.loadFollowingStreams();
//                case TAG_SETTINGS:
//                    SettingsFragment settingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag(TAG_SETTINGS);
////                    settingsFragment.getSettingsData();
//            }
//        } else {
//            Snackbar snackbar = Snackbar.make(parentLayout, "Please Check your internet connection!", Snackbar.LENGTH_LONG);
//            View customView = getLayoutInflater().inflate(R.layout.snackbar_internet_connection, null);
//            //snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
//            Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
//            snackbarLayout.setPadding(0, 0, 0, 0);
//            TextView tv_subject = customView.findViewById(R.id.tv_subject);
//            tv_subject.setText("Please Check your internet connection!");
//
//            snackbarLayout.addView(customView);
//            snackbar.show();
//        }
//    }
}

