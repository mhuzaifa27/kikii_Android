package com.example.kikkiapp.Activities.SignUpModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kikkiapp.Activities.MainActivity;
import com.example.kikkiapp.Adapters.GalleryPagerAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.AutoScrollViewPager;

import java.util.ArrayList;

public class SubscriptionActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final String TAG = "SubscriptionActivity";
    private Context mContext=SubscriptionActivity.this;

    public AutoScrollViewPager viewEvents;
    public LinearLayout viewDots;
    public int dotsCount;
    private ImageView[] dots;
    public GalleryPagerAdapter galleryPagerAdapter;
    private ArrayList<Integer> galleryList;

    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        initComponents();
        setViewPagerData();
        btn_next.setOnClickListener(this);
    }

    private void initComponents() {
        viewEvents = (AutoScrollViewPager) findViewById(R.id.viewEvents);
        viewDots = (LinearLayout) findViewById(R.id.viewDots);

        btn_next=findViewById(R.id.btn_next);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setPageViewIndicator(galleryPagerAdapter,viewEvents,viewDots,position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private void setViewPagerData() {
        galleryList = new ArrayList<>();
        galleryList.add(R.drawable.dummy_flower);
        galleryList.add(R.drawable.dummy_flower);
        galleryList.add(R.drawable.dummy_flower);
        galleryList.add(R.drawable.dummy_flower);
        galleryList.add(R.drawable.dummy_flower);
        galleryPagerAdapter = new GalleryPagerAdapter(this, galleryList);
        viewEvents.setAdapter(galleryPagerAdapter);
        viewEvents.setCurrentItem(0);
        viewEvents.setOnPageChangeListener(this);
        viewEvents.startAutoScroll();
        viewEvents.setInterval(5000);
        viewEvents.setCycle(true);
        viewEvents.setStopScrollWhenTouch(true);
        setPageViewIndicator(galleryPagerAdapter, viewEvents, viewDots);
    }
    private void setPageViewIndicator(final GalleryPagerAdapter galleryPagerAdapter, final AutoScrollViewPager viewEvents, final LinearLayout viewDots) {
        viewDots.removeAllViews();
        try {
            Log.d("###setPageViewIndicator", " : called");
            dotsCount = galleryPagerAdapter.getCount();
            dots = new ImageView[dotsCount];

            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        20,
                        20
                );
                params.setMargins(10, 0, 10, 0);

                final int presentPosition = i;
                dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        viewEvents.setCurrentItem(presentPosition);
                        return true;
                    }

                });
                viewDots.addView(dots[i], params);
            }
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setPageViewIndicator(final GalleryPagerAdapter galleryPagerAdapter, final AutoScrollViewPager viewEvents, final LinearLayout viewDots,int position) {
        viewDots.removeAllViews();
        try {
            Log.d("###setPageViewIndicator", " : called");
            dotsCount = galleryPagerAdapter.getCount();
            dots = new ImageView[dotsCount];

            for (int i = 0; i < dotsCount; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        20,
                        20
                );
                params.setMargins(10, 0, 10, 0);

                final int presentPosition = i;
                dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        viewEvents.setCurrentItem(presentPosition);
                        return true;
                    }

                });
                viewDots.addView(dots[i], params);
            }
            if(dots.length>0){
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                Intent loginIntent = new Intent(mContext, MainActivity.class);
                TaskStackBuilder.create(mContext).addNextIntentWithParentStack(loginIntent).startActivities();
                /*startActivity(new Intent(mContext, MainActivity.class));
                finish()*/;
                finish();
                break;
        }
    }
}
