package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.EventDetailActivity;
import com.example.kikkiapp.Model.Attendant;
import com.example.kikkiapp.Model.Event;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.ItemDecorator;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private List<Event> data;
    private Context context;
    private boolean isLoadingAdded=false;

    public EventsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_events, parent, false);
        return new EventViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final EventViewHolder holder, int position) {
        Event event = data.get(position);

        holder.tv_event_title.setText(event.getName());
        holder.tv_time.setText(event.getDatetime());
        Glide
                .with(context)
                .load(event.getCoverPic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.dummy_flower)
                .into(holder.img_event);

        List<Attendant> attendantList =event.getAttendants();
        attendantList.add(new Attendant(-1));
        EventAttendantsAdapter eventAttendantsAdapter=new EventAttendantsAdapter(attendantList,context);
        holder.rv_attendants.setAdapter(eventAttendantsAdapter);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, EventDetailActivity.class));
            }
        });
    }

    public void add(Event mc) {
        data.add(mc);
        if (data.size() > 1)
            notifyItemInserted(data.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<Event> mcList) {
        data = mcList;
        notifyDataSetChanged();
    }

    public void remove(Event city) {
        int position = data.indexOf(city);
        if (position > -1) {
            data.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Event());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = data.size() - 1;
        Event item = getItem(position);
        if (item != null) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Event getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tv_event_title, tv_time;
        SelectableRoundedImageView img_event;
        RecyclerView rv_attendants;

        public EventViewHolder(View itemView) {
            super(itemView);
            img_event=itemView.findViewById(R.id.img_event);

            tv_time=itemView.findViewById(R.id.tv_time);
            tv_event_title=itemView.findViewById(R.id.tv_event_title);

            rv_attendants =itemView.findViewById(R.id.rv_attendents);
            rv_attendants.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
            rv_attendants.addItemDecoration(new ItemDecorator(-20));
        }
    }
}
