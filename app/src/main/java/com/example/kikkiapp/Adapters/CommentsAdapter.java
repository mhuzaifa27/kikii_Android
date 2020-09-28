package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.CreatePostActivity;
import com.example.kikkiapp.Model.PostComment;
import com.example.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.PostCommentsViewHolder> {
    private List<PostComment> data;
    private Context context;
    private boolean isLoadingAdded;

    public CommentsAdapter(Context context) {
//        this.data = data;
        data=new ArrayList<>();
        this.context = context;
    }

    @Override
    public PostCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_comment, parent, false);
        return new PostCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PostCommentsViewHolder holder, int position) {
        PostComment postComment = data.get(position);

        holder.tv_name.setText(postComment.getCommenter().getName());
        holder.tv_comment.setText(postComment.getBody());
        Glide
                .with(context)
                .load(postComment.getCommenter().getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(holder.img_user);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CreatePostActivity.class));
            }
        });
    }
    public void add(PostComment mc) {
        data.add(mc);
        notifyItemInserted(data.size() - 1);
    }

    public void addAll(List <PostComment > mcList) {
        for (PostComment mc: mcList) {
            add(mc);
        }
    }

    public void remove(PostComment city) {
        int position = data.indexOf(city);
        if (position > -1) {
            data.remove(position);
            notifyItemRemoved(position);
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
        add(new PostComment());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = data.size() - 1;
        PostComment item = getItem(position);
        if (item != null) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public PostComment getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PostCommentsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_comment, tv_date;
        CircleImageView img_user;

        public PostCommentsViewHolder(View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_comment=itemView.findViewById(R.id.tv_comment);
            tv_date=itemView.findViewById(R.id.tv_date);
            img_user=itemView.findViewById(R.id.img_user);
        }
    }
}
