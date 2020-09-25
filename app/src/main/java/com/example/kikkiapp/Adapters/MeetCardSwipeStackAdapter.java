package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kikkiapp.Activities.MyProfileActivity;
import com.example.kikkiapp.R;

import java.util.List;

public class MeetCardSwipeStackAdapter extends RecyclerView.Adapter<MeetCardSwipeStackAdapter.TravelBuddyViewHolder> {
    private List<String> data;
    Context context;

    public MeetCardSwipeStackAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public TravelBuddyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_meet_swipe, parent, false);
        return new TravelBuddyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelBuddyViewHolder holder, int position) {
        String s = data.get(position);

        holder.img_close_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                holder.rl_detail_view.setVisibility(View.GONE);
                holder.ll_normal_view.setVisibility(View.VISIBLE);
            }
        });

        holder.img_open_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                holder.rl_detail_view.setVisibility(View.VISIBLE);
                holder.ll_normal_view.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TravelBuddyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_open_details,img_close_details;
        private LinearLayout ll_normal_view;
        private RelativeLayout rl_detail_view;


        public TravelBuddyViewHolder(View itemView) {
            super(itemView);
            img_open_details=itemView.findViewById(R.id.img_open_details);
            img_close_details=itemView.findViewById(R.id.img_close_details);

            ll_normal_view=itemView.findViewById(R.id.ll_normal_view);
            rl_detail_view=itemView.findViewById(R.id.rl_detail_view);
        }
    }
}
