package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kikkiapp.Model.Message;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.SessionManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChattingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> data;
    private Context context;
    private SessionManager sessionManager;

    public ChattingAdapter(List<Message> data, Context context) {
        this.data = data;
        this.context = context;
        sessionManager=new SessionManager(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).getSenderId().toString().equalsIgnoreCase(sessionManager.getUserID())) return 0;
        else return 1;
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
        Message message = data.get(position);
        switch (holder.getItemViewType()){
            case 0:
                SenderViewHolder senderViewHolder = (SenderViewHolder)holder;
                senderViewHolder.tv_message.setText(message.getBody());

                break;
            case 1:
                ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder)holder;
                receiverViewHolder.tv_message.setText(message.getBody());
                break;
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_message,tv_time_ago,tv_name;
        CircleImageView img_user;
        public SenderViewHolder(View itemView) {
            super(itemView);
            tv_message=itemView.findViewById(R.id.tv_message);
            tv_time_ago=itemView.findViewById(R.id.tv_time_ago);
            tv_name=itemView.findViewById(R.id.tv_name);
            img_user=itemView.findViewById(R.id.img_user);
        }
    }
    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView tv_message,tv_time_ago,tv_name;
        CircleImageView img_user;
        public ReceiverViewHolder(View itemView) {
            super(itemView);
            tv_message=itemView.findViewById(R.id.tv_message);
            tv_time_ago=itemView.findViewById(R.id.tv_time_ago);
            tv_name=itemView.findViewById(R.id.tv_name);
            img_user=itemView.findViewById(R.id.img_user);
        }
    }
}
