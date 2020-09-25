package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kikkiapp.Activities.PostDetailActivity;
import com.example.kikkiapp.R;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityPostsAdapter extends RecyclerView.Adapter<CommunityPostsAdapter.CommunityViewHolder> {
    private List<String> data;
    private Context context;
    private IClicks iClicks;

    public interface IClicks{
        void onLikeDislikeClick(View view,String data);
        void onCommentClick(View view,String data);
        void onShareClick(View view,String data);
    }
    public void setOnClickListeners(IClicks iClicks){
        this.iClicks=iClicks;
    }
    public CommunityPostsAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_community_posts, parent, false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommunityViewHolder holder, final int position) {
        final String s = data.get(position);


        /***CLICKS****/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PostDetailActivity.class));
            }
        });
        holder.tv_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClicks!=null){
                    iClicks.onLikeDislikeClick(v,s);
                }
            }
        });
        holder.tv_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClicks!=null){
                    iClicks.onCommentClick(v,s);
                }
            }
        });
        holder.tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClicks!=null){
                    iClicks.onShareClick(v,s);
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

        private TextView tv_likes,tv_comments,tv_share,tv_description,tv_name,tv_time_ago;
        private CircleImageView img_user;

        public CommunityViewHolder(View itemView) {
            super(itemView);
            tv_likes=itemView.findViewById(R.id.tv_likes);
            tv_comments=itemView.findViewById(R.id.tv_comments);
            tv_share=itemView.findViewById(R.id.tv_share);
            tv_description=itemView.findViewById(R.id.tv_description);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_time_ago=itemView.findViewById(R.id.tv_time_ago);

            img_user=itemView.findViewById(R.id.img_user);
        }
    }
}
