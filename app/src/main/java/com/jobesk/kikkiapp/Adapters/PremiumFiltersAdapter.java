package com.jobesk.kikkiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jobesk.kikkiapp.R;

import java.util.List;

public class PremiumFiltersAdapter extends RecyclerView.Adapter<PremiumFiltersAdapter.TravelBuddyViewHolder> {
    private List<String> data;
    Context context;

    public PremiumFiltersAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public TravelBuddyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_premium_filters, parent, false);
        return new TravelBuddyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelBuddyViewHolder holder, int position) {
        String s = data.get(position);
        holder.tv_status.setText(s);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TravelBuddyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_status;

        public TravelBuddyViewHolder(View itemView) {
            super(itemView);
            tv_status=itemView.findViewById(R.id.tv_status);
        }
    }
}
