package com.jobesk.kikkiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jobesk.kikkiapp.R;

import java.util.List;

public class ChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> data;
    Context context;

    public ChattingAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).equalsIgnoreCase("1")) return 1;
        else return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        RecyclerView.ViewHolder holder=null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.item_sent_message, parent, false);
                holder=new SenderViewHolder(view);
                break;
            case 1:
                view = inflater.inflate(R.layout.item_received_message, parent, false);
                holder=new ReceiverViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String s = data.get(position);
        switch (holder.getItemViewType()){
            case 0:
                SenderViewHolder senderViewHolder = (SenderViewHolder)holder;
                break;
            case 1:
                ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder)holder;
                break;
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        public SenderViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        public ReceiverViewHolder(View itemView) {
            super(itemView);
        }
    }
}
