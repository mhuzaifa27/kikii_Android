package com.jobesk.kikkiapp.Fragments.Main;

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

import com.jobesk.kikkiapp.Fragments.Others.CommunityFragment;
import com.jobesk.kikkiapp.Fragments.Others.EventsFragment;
import com.jobesk.kikkiapp.Fragments.Others.KikiiFragment;
import com.jobesk.kikkiapp.R;

public class SocialFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private FrameLayout tab_frame;
    private ImageView img_select_kikii, img_selected_kikii,
            img_select_event, img_selected_event,
            img_select_community, img_selected_community;

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

        return view;
    }

    private void initComponents(View view) {
        mHandler = new Handler();

        communityFragment = new CommunityFragment();
        eventsFragment = new EventsFragment();
        kikiiFragment = new KikiiFragment();

        tab_frame = view.findViewById(R.id.tab_frame);

        img_select_kikii=view.findViewById(R.id.img_select_kikii);
        img_selected_kikii=view.findViewById(R.id.img_selected_kikii);
        img_select_event=view.findViewById(R.id.img_select_event);
        img_selected_event=view.findViewById(R.id.img_selected_event);
        img_select_community=view.findViewById(R.id.img_select_community);
        img_selected_community=view.findViewById(R.id.img_selected_community);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_select_community:
                selectCommunity();
                break;
            case R.id.img_select_event:
                selectEvent();
                break;
            case R.id.img_select_kikii:
                selectKikii();
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

        navItemIndex=2;
        loadFragment();
    }

    private void selectEvent() {
        img_select_community.setVisibility(View.VISIBLE);
        img_select_event.setVisibility(View.GONE);
        img_select_kikii.setVisibility(View.VISIBLE);

        img_selected_community.setVisibility(View.GONE);
        img_selected_event.setVisibility(View.VISIBLE);
        img_selected_kikii.setVisibility(View.GONE);
        navItemIndex=1;
        loadFragment();
    }

    private void selectCommunity() {
        img_select_community.setVisibility(View.GONE);
        img_select_event.setVisibility(View.VISIBLE);
        img_select_kikii.setVisibility(View.VISIBLE);

        img_selected_community.setVisibility(View.VISIBLE);
        img_selected_event.setVisibility(View.GONE);
        img_selected_kikii.setVisibility(View.GONE);

        navItemIndex=0;
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
