package com.jobesk.kikkiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.jobesk.kikkiapp.R;

public class OnBoardPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;

    public OnBoardPagerAdapter(Context context) {
        this.context = context;
    }

    public int [] slideImages={
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4
    };

    public String [] slideHeadings={
            "Find",
            "Match",
            "Chat",
            "News Feed"
    };

    public String [] slideDescriptions={
            "Find your perfect romantic relationship or friendship.\n" +
                    "\n" +
                    "Find Real People - Every Profile is verified",
            "Once matched you have 24 hours to start a conversation.\n" +
                    "\n" +
                    "Send voice notes and pictures to your match.",
            "Virtually date anyone from anywhere around the UK & Ireland.\n" +
                    "\n" +
                    "Connect and chat via voice call \n" +
                    "(No personal phone numbers given out)",
            "Keep up with the Kikii Community via the Social and Events feed\n" +
                    "\n" +
                    "If you’re offered a seat on a rocket ship, don’t ask what seat! Just get on."
    };

    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ScrollView)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
      inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
      View view=inflater.inflate(R.layout.sliding_layout,container,false);

        ImageView img_view_pager=view.findViewById(R.id.img_view_pager);
        TextView tv_heading_pager=view.findViewById(R.id.tv_heading_pager);
        TextView tv_description_pager=view.findViewById(R.id.tv_description_pager);

        img_view_pager.setImageResource(slideImages[position]);
        tv_heading_pager.setText(slideHeadings[position]);
        tv_description_pager.setText(slideDescriptions[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ScrollView)object);
    }
}
