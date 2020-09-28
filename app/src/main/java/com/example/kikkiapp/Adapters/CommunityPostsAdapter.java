package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.PostDetailActivity;
import com.example.kikkiapp.Model.Post;
import com.example.kikkiapp.R;
import com.fasterxml.jackson.databind.node.POJONode;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.POST;

public class CommunityPostsAdapter extends RecyclerView.Adapter<CommunityPostsAdapter.CommunityViewHolder> {
    private List<Post> data;
    private Context context;
    private IClicks iClicks;
    private boolean isLoadingAdded;

    public interface IClicks {
        void onLikeDislikeClick(View view, Post data);

        void onCommentClick(View view, Post data);

        void onShareClick(View view, Post data);
    }

    public void setOnClickListeners(IClicks iClicks) {
        this.iClicks = iClicks;
    }

    public CommunityPostsAdapter(Context context) {
        this.data = data;
        data = new ArrayList<>();
        this.context = context;
    }

    public void add(Post mc) {
        data.add(mc);
        notifyItemInserted(data.size() - 1);
    }

    public void addAll(List<Post> mcList) {
        for (Post mc : mcList) {
            add(mc);
        }
    }

    public void remove(Post city) {
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
        add(new Post());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = data.size() - 1;
        Post item = getItem(position);
        if (item != null) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Post getItem(int position) {
        return data.get(position);
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_community_posts, parent, false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommunityViewHolder holder, final int position) {
        final Post post = data.get(position);

        holder.tv_name.setText(post.getUser().getName());
        holder.tv_description.setText(post.getBody());
        holder.tv_likes.setText(String.valueOf(post.getLikesCount()));
        holder.tv_comments.setText(String.valueOf(post.getCommentsCount()));
        Glide
                .with(context)
                .load(post.getUser().getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(holder.img_user);

        /***CLICKS****/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClicks != null) {
                    if (post.getCommentsCount() > 0)
                        iClicks.onLikeDislikeClick(v, post);
                }
            }
        });
        holder.tv_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClicks != null) {
                    iClicks.onLikeDislikeClick(v, post);
                }
            }
        });
        holder.tv_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClicks != null) {
                    iClicks.onCommentClick(v, post);
                }
            }
        });
        holder.tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClicks != null) {
                    if (post.getCommentsCount() > 0)
                        iClicks.onShareClick(v, post);
                }
            }
        });
        /************/
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CommunityViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_likes, tv_comments, tv_share, tv_description, tv_name, tv_time_ago;
        private CircleImageView img_user;

        public CommunityViewHolder(View itemView) {
            super(itemView);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_share = itemView.findViewById(R.id.tv_share);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time_ago = itemView.findViewById(R.id.tv_time_ago);

            img_user = itemView.findViewById(R.id.img_user);
        }
    }
}
