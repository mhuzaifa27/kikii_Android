package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.ChattingActivity;
import com.example.kikkiapp.Model.Conversation;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainChatAdapter extends RecyclerView.Adapter<MainChatAdapter.TravelBuddyViewHolder> {
    private List<Conversation> data;
    Context context;

    public MainChatAdapter(List<Conversation> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public TravelBuddyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_main_chat, parent, false);
        return new TravelBuddyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelBuddyViewHolder holder, int position) {
        final Conversation conversation = data.get(position);

        holder.tv_name.setText(conversation.getParticipant2().getName());
        if (conversation.getMessages().size() > 0)
            holder.tv_last_message.setText(conversation.getMessages().get(0).getBody());
        else
            holder.tv_last_message.setText(".......");
        Glide
                .with(context)
                .load(conversation.getParticipant2().getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(holder.img_user);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conversation.getMessages().size() > 0) {
                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra(Constant.CONVERSATION_ID, String.valueOf(conversation.getMessages().get(0).getConversationId()));
                    intent.putExtra(Constant.USER_MATCH_ID, String.valueOf(conversation.getParticipant2Id()));
                    context.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TravelBuddyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_last_message, tv_date;
        CircleImageView img_user;
        LinearLayout ll_notify;

        public TravelBuddyViewHolder(View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_user);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_last_message = itemView.findViewById(R.id.tv_last_message);

        }
    }
}
