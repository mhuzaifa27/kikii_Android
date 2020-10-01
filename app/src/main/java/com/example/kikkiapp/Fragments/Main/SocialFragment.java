package com.example.kikkiapp.Fragments.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.kikkiapp.Activities.CreatePostActivity;
import com.example.kikkiapp.Activities.EventDetailActivity;
import com.example.kikkiapp.Fragments.Others.CommunityFragment;
import com.example.kikkiapp.Fragments.Others.EventsFragment;
import com.example.kikkiapp.Fragments.Others.KikiiFragment;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.R;

public class SocialFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = "SocialFragment";
    private Context context;
    private FrameLayout tab_frame;
    private ImageView img_select_kikii, img_selected_kikii,
            img_select_event, img_selected_event,
            img_select_community, img_selected_community, img_add, img_kikii_info;

    private CommunityFragment communityFragment;
    private EventsFragment eventsFragment;
    private KikiiFragment kikiiFragment;

    public static final String TAG_COMMUNITY = "tag_community";
    public static final String TAG_EVENTS = "tag_events";
    public static final String TAG_KIKII = "tag_kikii";
    public static final String CURRENT_TAG = TAG_COMMUNITY;
    public static int navItemIndex = 0;

    private Handler mHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);

        initComponents(view);
        loadFragment();

        img_select_community.setOnClickListener(this);
        img_select_event.setOnClickListener(this);
        img_select_kikii.setOnClickListener(this);
        img_add.setOnClickListener(this);

        return view;
    }

    private void initComponents(View view) {
        context = getContext();
        mHandler = new Handler();

        communityFragment = new CommunityFragment();
        eventsFragment = new EventsFragment();
        kikiiFragment = new KikiiFragment();

        tab_frame = view.findViewById(R.id.tab_frame);

        img_select_kikii = view.findViewById(R.id.img_select_kikii);
        img_selected_kikii = view.findViewById(R.id.img_selected_kikii);
        img_select_event = view.findViewById(R.id.img_select_event);
        img_selected_event = view.findViewById(R.id.img_selected_event);
        img_select_community = view.findViewById(R.id.img_select_community);
        img_selected_community = view.findViewById(R.id.img_selected_community);

        img_add = view.findViewById(R.id.img_add);
        img_kikii_info = view.findViewById(R.id.img_kikii_info);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_select_community:
                selectCommunity();
                break;
            case R.id.img_select_event:
                selectEvent();
                break;
            case R.id.img_select_kikii:
                selectKikii();
                break;
            case R.id.img_add:
                if (navItemIndex == 0) startActivity(new Intent(context, CreatePostActivity.class));
                else startActivity(new Intent(context, EventDetailActivity.class));
                break;
            case R.id.img_kikii_info:
                //startActivity(new Intent(context, CreatePostActivity.class));
                break;
        }
    }

    private void selectKikii() {
        img_select_community.setVisibility(View.VISIBLE);
        img_select_event.setVisibility(View.VISIBLE);
        img_select_kikii.setVisibility(View.GONE);

        img_selected_community.setVisibility(View.GONE);
        img_selected_event.setVisibility(View.GONE);
        img_selected_kikii.setVisibility(View.VISIBLE);

        navItemIndex = 2;
        img_kikii_info.setVisibility(View.VISIBLE);
        img_add.setVisibility(View.GONE);
        loadFragment();
    }

    private void selectEvent() {
        img_select_community.setVisibility(View.VISIBLE);
        img_select_event.setVisibility(View.GONE);
        img_select_kikii.setVisibility(View.VISIBLE);

        img_selected_community.setVisibility(View.GONE);
        img_selected_event.setVisibility(View.VISIBLE);
        img_selected_kikii.setVisibility(View.GONE);

        navItemIndex = 1;
        img_kikii_info.setVisibility(View.GONE);
        img_add.setVisibility(View.GONE);
        loadFragment();
    }

    private void selectCommunity() {
        img_select_community.setVisibility(View.GONE);
        img_select_event.setVisibility(View.VISIBLE);
        img_select_kikii.setVisibility(View.VISIBLE);

        img_selected_community.setVisibility(View.VISIBLE);
        img_selected_event.setVisibility(View.GONE);
        img_selected_kikii.setVisibility(View.GONE);

        navItemIndex = 0;
        img_kikii_info.setVisibility(View.GONE);
        img_add.setVisibility(View.VISIBLE);
        loadFragment();
    }

    private void loadFragment() {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
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
                return communityFragment;
            case 1:
                return eventsFragment;
            case 2:
                return kikiiFragment;
            default:
                return new CommunityFragment();
        }
    }
}
