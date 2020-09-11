package com.jobesk.kikkiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jobesk.kikkiapp.Activities.EventDetailActivity;
import com.jobesk.kikkiapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.TravelBuddyViewHolder> {
    private List<String> data;
    Context context;

    public EventsAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public TravelBuddyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_events, parent, false);
        return new TravelBuddyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelBuddyViewHolder holder, int position) {
        String s = data.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EventDetailActivity.class));
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TravelBuddyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_subject, tv_body, tv_date;
        CircleImageView img_user;
        LinearLayout ll_notify;

        public TravelBuddyViewHolder(View itemView) {
            super(itemView);


        }
    }
}
