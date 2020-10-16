package com.example.kikkiapp.Fragments.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.kikkiapp.Activities.FiltersActivity;
import com.example.kikkiapp.Activities.MyProfileActivity;
import com.example.kikkiapp.Activities.SupportActivity;
import com.example.kikkiapp.Adapters.MeetCardSwipeStackAdapter;
import com.example.kikkiapp.R;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;

public class MeetFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = "MeetFragment";
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<String> list = new ArrayList<>();
    private MeetCardSwipeStackAdapter meetCardSwipeStackAdapter;
    private ImageView img_open_details, img_close_details, img_menu, img_filters, img_search;
    private LinearLayout ll_normal_view, ll_menu;
    private RelativeLayout rl_detail_view;

    private CardStackView cardStackView;
    private CardStackLayoutManager cardStackLayoutManager;
    private SwipeAnimationSetting swipeAnimationSetting;
    private boolean isMenuVisible = false;
    private TextView tv_profile, tv_support, tv_logout;
    private ImageView img_main;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meet, container, false);
        initComponents(view);

        img_menu.setOnClickListener(this);
        img_filters.setOnClickListener(this);
        img_search.setOnClickListener(this);
        tv_profile.setOnClickListener(this);
        tv_support.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
        img_main.setOnClickListener(this);

        return view;
    }

    private void initComponents(View view) {
        context = getContext();

        img_open_details = view.findViewById(R.id.img_open_details);
        img_close_details = view.findViewById(R.id.img_close_details);
        img_menu = view.findViewById(R.id.img_menu);
        img_filters = view.findViewById(R.id.img_filters);
        img_search = view.findViewById(R.id.img_search);

        ll_normal_view = view.findViewById(R.id.ll_normal_view);
        ll_menu = view.findViewById(R.id.ll_menu);

        rl_detail_view = view.findViewById(R.id.rl_detail_view);
        img_main =view.findViewById(R.id.img_main);

        cardStackView = view.findViewById(R.id.card_stack_view_frame);

        tv_profile = view.findViewById(R.id.tv_profile);
        tv_support = view.findViewById(R.id.tv_support);
        tv_logout = view.findViewById(R.id.tv_logout);

        list.add("php");
        list.add("c");
        list.add("python");
        list.add("java");

        setCardSwipe();
    }

    @Override
    public void onRefresh() {

    }

    private void setCardSwipe() {
        cardStackLayoutManager = new CardStackLayoutManager(context);
        cardStackLayoutManager.setStackFrom(StackFrom.Top);
        cardStackLayoutManager.setMaxDegree(20.0f);
        cardStackLayoutManager.setDirections(Direction.HORIZONTAL);
        cardStackLayoutManager.setSwipeThreshold(0.5f);
        cardStackLayoutManager.setCanScrollHorizontal(true);
        cardStackLayoutManager.setCanScrollVertical(false);
        cardStackView.setLayoutManager(cardStackLayoutManager);
        meetCardSwipeStackAdapter = new MeetCardSwipeStackAdapter(list, context);
        cardStackView.setAdapter(meetCardSwipeStackAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_menu:
                Log.d(TAG, "onClick: img_menu");
                if (isMenuVisible)
                    closeMenu();
                else
                    openMenu();
                break;
            case R.id.img_filters:
                startActivity(new Intent(context, FiltersActivity.class));
                break;
            case R.id.img_search:
                break;
            case R.id.tv_profile:
                closeMenu();
                startActivity(new Intent(context, MyProfileActivity.class));
                break;
            case R.id.tv_support:
                closeMenu();
                startActivity(new Intent(context, SupportActivity.class));
                break;
            case R.id.tv_logout:
            case R.id.img_main:
                closeMenu();
                break;
        }
    }

    private void openMenu() {
        isMenuVisible=true;
        ll_menu.setVisibility(View.VISIBLE);
    }

    private void closeMenu() {
        isMenuVisible=false;
        ll_menu.setVisibility(View.GONE);
    }
}
