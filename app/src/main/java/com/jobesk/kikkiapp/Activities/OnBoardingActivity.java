package com.jobesk.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jobesk.kikkiapp.Activities.SignUpModule.SignUpActivity;
import com.jobesk.kikkiapp.Adapters.OnBoardPagerAdapter;
import com.jobesk.kikkiapp.R;

public class OnBoardingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "OnBoardingActivity";
    private Context mContext=OnBoardingActivity.this;

    private ViewPager vp_on_boarding;
    private LinearLayout ll_dots;
    private TextView[] mDots;
    private TextView tv_skip;

    private OnBoardPagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        initComponents();
        addDotsIndicator(0);

        tv_skip.setOnClickListener(this);
        vp_on_boarding.addOnPageChangeListener(viewPagerListener);
    }

    private void initComponents() {
        vp_on_boarding=findViewById(R.id.vp_on_boarding);
        ll_dots=findViewById(R.id.ll_dots);

        pagerAdapter=new OnBoardPagerAdapter(mContext);
        vp_on_boarding.setAdapter(pagerAdapter);

        tv_skip=findViewById(R.id.tv_skip);
    }

    private void addDotsIndicator(int position){
        mDots=new TextView[4];
        ll_dots.removeAllViews();
        for (int i = 0; i <mDots.length ; i++) {
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.light_grey));

            ll_dots.addView(mDots[i]);
        }

        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    ViewPager.OnPageChangeListener viewPagerListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        startActivity(new Intent(mContext, SignUpActivity.class));
        finish();
    }
}
