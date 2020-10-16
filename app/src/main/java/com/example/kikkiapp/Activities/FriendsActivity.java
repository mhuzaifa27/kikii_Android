package com.example.kikkiapp.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.kikkiapp.Fragments.Others.MyFriendsFragment;
import com.example.kikkiapp.Fragments.Others.PendingFragment;
import com.example.kikkiapp.Fragments.Others.SentRequestsFragment;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CommonMethods;

public class FriendsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private FrameLayout tab_frame;
    private ImageView img_select_sent_requests, img_selected_sent_requests,
            img_select_pending, img_selected_pending,
            img_select_my_friends, img_selected_my_friends;

    private MyFriendsFragment myFriendsFragment;
    private PendingFragment pendingFragment;
    private SentRequestsFragment sentRequestsFragment;

    public static final String TAG_MY_FRIENDS = "tag_my_friends";
    public static final String TAG_PENDING = "tag_pending";
    public static final String TAG_SENT_REQUESTS = "tag_sent_requests";
    public static final String CURRENT_TAG = TAG_MY_FRIENDS;
    public static int navItemIndex = 0;

    private Handler mHandler;
    private ImageView img_back;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        initComponents();
        loadFragment();

        img_select_my_friends.setOnClickListener(this);
        img_select_pending.setOnClickListener(this);
        img_select_sent_requests.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    private void initComponents() {
        mHandler = new Handler();

        myFriendsFragment = new MyFriendsFragment();
        pendingFragment = new PendingFragment();
        sentRequestsFragment = new SentRequestsFragment();

        tab_frame = findViewById(R.id.tab_frame);

        img_select_sent_requests =findViewById(R.id.img_select_sent_requests);
        img_selected_sent_requests =findViewById(R.id.img_selected_sent_requests);
        img_select_pending =findViewById(R.id.img_select_pending);
        img_selected_pending =findViewById(R.id.img_selected_pending);
        img_select_my_friends =findViewById(R.id.img_select_my_friends);
        img_selected_my_friends =findViewById(R.id.img_selected_my_friends);

        img_back=findViewById(R.id.img_back);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_select_my_friends:
                selectMyFriends();
                break;
            case R.id.img_select_pending:
                selectPending();
                break;
            case R.id.img_select_sent_requests:
                selectSentRequests();
                break;
            case R.id.img_back:
                CommonMethods.goBack(this);
                break;
        }
    }

    private void selectSentRequests() {
        img_select_my_friends.setVisibility(View.VISIBLE);
        img_select_pending.setVisibility(View.VISIBLE);
        img_select_sent_requests.setVisibility(View.GONE);

        img_selected_my_friends.setVisibility(View.GONE);
        img_selected_pending.setVisibility(View.GONE);
        img_selected_sent_requests.setVisibility(View.VISIBLE);

        navItemIndex=2;
        loadFragment();
    }

    private void selectPending() {
        img_select_my_friends.setVisibility(View.VISIBLE);
        img_select_pending.setVisibility(View.GONE);
        img_select_sent_requests.setVisibility(View.VISIBLE);

        img_selected_my_friends.setVisibility(View.GONE);
        img_selected_pending.setVisibility(View.VISIBLE);
        img_selected_sent_requests.setVisibility(View.GONE);
        navItemIndex=1;
        loadFragment();
    }

    private void selectMyFriends() {
        img_select_my_friends.setVisibility(View.GONE);
        img_select_pending.setVisibility(View.VISIBLE);
        img_select_sent_requests.setVisibility(View.VISIBLE);

        img_selected_my_friends.setVisibility(View.VISIBLE);
        img_selected_pending.setVisibility(View.GONE);
        img_selected_sent_requests.setVisibility(View.GONE);

        navItemIndex=0;
        loadFragment();
    }

    private void loadFragment() {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                /*fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);*/
                fragmentTransaction.replace(R.id.tab_frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }
    private Fragment getFragment() {
        switch (navItemIndex) {
            case 0:
                return myFriendsFragment;
            case 1:
                return pendingFragment;
            case 2:
                return sentRequestsFragment;
            default:
                return new MyFriendsFragment();
        }
    }
}
