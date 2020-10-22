package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.kikkiapp.R;
import com.joooonho.SelectableRoundedImageView;

public class OnBoardPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;

    public OnBoardPagerAdapter(Context context) {
        this.context = context;
    }

    public int [] slideImages={
            R.drawable.dummy_find,
            R.drawable.dummy_match,
            R.drawable.dummy_chat,
            R.drawable.dummy_news_feed
    };

    public String [] slideHeadings={
            "Find",
            "Match",
            "Chat",
            "News Feed"
    };

    public String [] slideDescriptions={
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero",
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero",
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero",
            "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero"
    };

    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
      inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
      View view=inflater.inflate(R.layout.sliding_layout,container,false);

        SelectableRoundedImageView img_view_pager=view.findViewById(R.id.img_view_pager);
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
        container.removeView((LinearLayout)object);
    }
}
