package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kikkiapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KikiiPostsAdapter extends RecyclerView.Adapter<KikiiPostsAdapter.TravelBuddyViewHolder> {
    private List<String> data;
    Context context;

    public KikiiPostsAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public TravelBuddyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_kikii_posts, parent, false);
        return new TravelBuddyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelBuddyViewHolder holder, int position) {
        String s = data.get(position);
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
