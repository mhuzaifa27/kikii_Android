package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.MyProfileActivity;
import com.example.kikkiapp.Model.MeetUser;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.UtilityFunctions;
import com.joooonho.SelectableRoundedImageView;

import java.util.List;

public class MeetCardSwipeStackAdapter extends RecyclerView.Adapter<MeetCardSwipeStackAdapter.TravelBuddyViewHolder> {
    private List<MeetUser> data;
    private Context context;
    private IListEnd iListEnd;
    private IClick iClick;

    public interface IListEnd{
        void onListEndListener();
    }
    public interface IClick{
        void onLikeUserClick(MeetUser user);
        void onDislikeUserClick(MeetUser user);
        void onFollowUserClick(MeetUser user);
    }
    public void setOnListEndListener(IListEnd iListEnd){
        this.iListEnd=iListEnd;
    }
    public void setOnClickListener(IClick iClick){
        this.iClick=iClick;
    }

    public MeetCardSwipeStackAdapter(List<MeetUser> data, Context context) {
        this.data = data;
        this.context = context;
    }
    @Override
    public TravelBuddyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_meet_swipe, parent, false);
        return new TravelBuddyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelBuddyViewHolder holder, int position) {
        final MeetUser user = data.get(position);

       /***Set User Data***/
        holder.tv_user_name.setText(user.getName()+",");
        holder.tv_friends_count.setText("("+user.getFriends_count()+" friends)");
        if (user.getPronouns() != null){
            holder.tv_pronoun_1.setText(user.getPronouns());
            holder.tv_pronoun_2.setText(user.getPronouns());
        }
        if (user.getGenderIdentity() != null){
            holder.tv_gender_s.setText(user.getGenderIdentity());
            holder.tv_gender_2.setText(user.getGenderIdentity());
        }
        if (user.getBio() != null)
            holder.tv_bio.setText(user.getBio());
        /***CURIOSITIES**/
        if (user.getRelationshipStatus() != null)
            holder.tv_relationship_status.setText(user.getRelationshipStatus());
        if (user.getHeight() != null)
            holder.tv_height.setText(user.getHeight());
        if (user.getLookingFor() != null)
            holder.tv_looking_for.setText(user.getLookingFor());
        if (user.getSmoke() != null)
            holder.tv_cigerate.setText(user.getSmoke());
        if (user.getDrink() != null)
            holder.tv_drink.setText(user.getDrink());
        if (user.getCannabis() != null)
            holder.tv_canabiese.setText(user.getCannabis());
        if (user.getPoliticalViews() != null)
            holder.tv_political_views.setText(user.getPoliticalViews());
        if (user.getReligion() != null)
            holder.tv_religion.setText(user.getReligion());
        if (user.getDietLike() != null)
            holder.tv_diet.setText(user.getDietLike());
        if (user.getSign() != null)
            holder.tv_sign.setText(user.getSign());
        if (user.getPets() != null)
            holder.tv_pet.setText(user.getPets());
        if (user.getKids() != null)
            holder.tv_children.setText(user.getKids());
        Glide
                .with(context)
                .load(user.getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(holder.img_user);
        holder.tv_user_age.setText(String.valueOf(user.getAge()));
        if(user.getShow_location()==1){
            holder.tv_distance.setText(user.getDistance());
            holder.tv_distance2.setText(user.getDistance());
        }
        else{
            holder.tv_distance.setVisibility(View.GONE);
            holder.tv_distance2.setVisibility(View.GONE);
        }
       /******/

        if(position==data.size()){
            if(iListEnd!=null){
                iListEnd.onListEndListener();
            }
        }
        holder.img_like_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClick!=null){
                    iClick.onLikeUserClick(user);
                }
            }
        });
        holder.img_dislike_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClick!=null){
                    iClick.onDislikeUserClick(user);
                }
            }
        });
        holder.img_follow_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClick!=null){
                    iClick.onFollowUserClick(user);
                }
            }
        });
        holder.btn_add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iClick!=null){
                    iClick.onFollowUserClick(user);
                }
            }
        });

        holder.img_close_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                holder.rl_detail_view.setVisibility(View.GONE);
                holder.ll_normal_view.setVisibility(View.VISIBLE);
            }
        });

        holder.img_open_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                holder.rl_detail_view.setVisibility(View.VISIBLE);
                holder.ll_normal_view.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TravelBuddyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_open_details,img_close_details;
        private LinearLayout ll_normal_view;
        private RelativeLayout rl_detail_view;
        private SelectableRoundedImageView img_user;
        private TextView tv_user_name,tv_user_age,tv_distance,tv_gender_s,tv_gender_2,tv_pronoun_1,tv_pronoun_2,tv_distance2,tv_bio
                ,tv_friends_count;
        private Button btn_view_posts,btn_view_friends,btn_add_friend;
        private TextView tv_relationship_status,tv_height,tv_looking_for,tv_cigerate,tv_drink,tv_canabiese,tv_political_views,tv_religion,tv_diet,tv_sign,tv_pet,tv_children;
        private ImageView img_like_user,img_dislike_user,img_follow_user;

        public TravelBuddyViewHolder(View itemView) {
            super(itemView);
            tv_user_name=itemView.findViewById(R.id.tv_user_name);
            tv_user_age=itemView.findViewById(R.id.tv_user_age);
            tv_distance=itemView.findViewById(R.id.tv_distance);
            tv_gender_s=itemView.findViewById(R.id.tv_gender_s);
            tv_gender_2=itemView.findViewById(R.id.tv_gender_2);
            tv_pronoun_1=itemView.findViewById(R.id.tv_pronoun_1);
            tv_pronoun_2=itemView.findViewById(R.id.tv_pronoun_2);
            tv_distance2=itemView.findViewById(R.id.tv_distance2);
            tv_bio=itemView.findViewById(R.id.tv_bio);
            tv_friends_count=itemView.findViewById(R.id.tv_friends_count);

            tv_relationship_status=itemView.findViewById(R.id.tv_relationship_status);
            tv_height=itemView.findViewById(R.id.tv_height);
            tv_looking_for=itemView.findViewById(R.id.tv_looking_for);
            tv_cigerate=itemView.findViewById(R.id.tv_cigerate);
            tv_drink=itemView.findViewById(R.id.tv_drink);
            tv_canabiese=itemView.findViewById(R.id.tv_canabiese);
            tv_political_views=itemView.findViewById(R.id.tv_political_views);
            tv_religion=itemView.findViewById(R.id.tv_religion);
            tv_diet=itemView.findViewById(R.id.tv_diet);
            tv_sign=itemView.findViewById(R.id.tv_sign);
            tv_pet=itemView.findViewById(R.id.tv_pet);
            tv_children=itemView.findViewById(R.id.tv_children);

            btn_view_posts=itemView.findViewById(R.id.btn_view_posts);
            btn_view_friends=itemView.findViewById(R.id.btn_view_friends);
            btn_add_friend=itemView.findViewById(R.id.btn_add_friend);

            img_open_details=itemView.findViewById(R.id.img_open_details);
            img_close_details=itemView.findViewById(R.id.img_close_details);
            img_like_user=itemView.findViewById(R.id.img_like_user);
            img_dislike_user=itemView.findViewById(R.id.img_dislike_user);
            img_follow_user=itemView.findViewById(R.id.img_follow_user);

            ll_normal_view=itemView.findViewById(R.id.ll_normal_view);
            rl_detail_view=itemView.findViewById(R.id.rl_detail_view);

            img_user=itemView.findViewById(R.id.img_user);
        }
    }
}
