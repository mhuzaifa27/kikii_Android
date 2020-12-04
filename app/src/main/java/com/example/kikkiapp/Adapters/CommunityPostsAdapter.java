package com.example.kikkiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Model.Post;
import com.example.kikkiapp.Model.PostMedia;
import com.example.kikkiapp.R;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityPostsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Post> data;
    private Context context;
    private IClicks iClicks;
    private boolean isLoadingAdded;

    public interface IClicks {
        void onLikeDislikeClick(View view, Post data, int position, TextView tv_likes);

        void onCommentClick(View view, Post data);

        void onShareClick(View view, Post data);

        void onMenuClick(View view, Post data, int position);
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
        if (data.size() > 1)
            notifyItemInserted(data.size() - 1);
        notifyDataSetChanged();
    }

    public void addList(List<Post> mc) {
        if (mc.size() > 0) {
            for (int i = 0; i < mc.size(); i++) {
                data.add(mc.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public void addAll(List<Post> mcList) {
        data = mcList;
        notifyDataSetChanged();
    }

    public void remove(Post city) {
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
    public int getItemViewType(int position) {
        int mediaArraySize = data.get(position).getMedia().size();
        if (mediaArraySize > 0) {
            if (mediaArraySize == 1) {
                /*if (1 == 1) {
                    return 1;
                } else {*/
                return 2;
                //}
            } else {
                return 3;
            }
        } else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.item_community_posts, parent, false);
                holder = new CommunityViewHolder(view);
                break;
            case 1:
                view = inflater.inflate(R.layout.item_community_posts_with_video, parent, false);
                holder = new VideoPostViewHolder(view);
                break;
            case 2:
                view = inflater.inflate(R.layout.item_community_posts_with_single_image, parent, false);
                holder = new SingleImagePostViewHolder(view);
                break;
            case 3:
                view = inflater.inflate(R.layout.item_community_posts_with_multiple_images, parent, false);
                holder = new MultipleImagePostViewHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, final int position) {
        final Post post = data.get(position);

        switch (mainHolder.getItemViewType()) {
            case 0:
                final CommunityViewHolder holder = (CommunityViewHolder) mainHolder;
                holder.tv_name.setText(post.getUser().getName());
                holder.tv_description.setText(post.getBody());
                holder.tv_likes.setText(String.valueOf(post.getLikesCount()));
                holder.tv_comments.setText(String.valueOf(post.getCommentsCount()));
                holder.tv_time_ago.setText(post.getCreatedAt());

                if (post.getIsLiked().toString().equalsIgnoreCase("0"))
                    holder.tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty_heart, 0, 0, 0);
                else
                    holder.tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fill_heart, 0, 0, 0);

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
                holder.img_post_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onMenuClick(v, post, position);
                        }
                    }
                });
                holder.tv_likes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onLikeDislikeClick(v, post, position, holder.tv_likes);
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
                break;
            case 1:
                final VideoPostViewHolder videoPostViewHolder = (VideoPostViewHolder) mainHolder;
                videoPostViewHolder.tv_name.setText(post.getUser().getName());
                videoPostViewHolder.tv_description.setText(post.getBody());
                videoPostViewHolder.tv_likes.setText(String.valueOf(post.getLikesCount()));
                videoPostViewHolder.tv_comments.setText(String.valueOf(post.getCommentsCount()));

                if (post.getIsLiked().toString().equalsIgnoreCase("0"))
                    videoPostViewHolder.tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty_heart, 0, 0, 0);
                else
                    videoPostViewHolder.tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fill_heart, 0, 0, 0);

                Glide
                        .with(context)
                        .load(post.getUser().getProfilePic())
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(videoPostViewHolder.img_user);

                /***CLICKS****/
                videoPostViewHolder.img_post_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onMenuClick(v, post, position);
                        }
                    }
                });
                videoPostViewHolder.tv_likes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onLikeDislikeClick(v, post, position, videoPostViewHolder.tv_likes);
                        }
                    }
                });
                videoPostViewHolder.tv_comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onCommentClick(v, post);
                        }
                    }
                });
                videoPostViewHolder.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            if (post.getCommentsCount() > 0)
                                iClicks.onShareClick(v, post);
                        }
                    }
                });
                /************/
                break;
            case 2:
                final SingleImagePostViewHolder singleImagePostViewHolder = (SingleImagePostViewHolder) mainHolder;
                singleImagePostViewHolder.tv_name.setText(post.getUser().getName());
                singleImagePostViewHolder.tv_description.setText(post.getBody());
                singleImagePostViewHolder.tv_likes.setText(String.valueOf(post.getLikesCount()));
                singleImagePostViewHolder.tv_comments.setText(String.valueOf(post.getCommentsCount()));
                singleImagePostViewHolder.tv_time_ago.setText(post.getCreatedAt());

                if (post.getIsLiked().toString().equalsIgnoreCase("0"))
                    singleImagePostViewHolder.tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty_heart, 0, 0, 0);
                else
                    singleImagePostViewHolder.tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fill_heart, 0, 0, 0);

                Glide
                        .with(context)
                        .load(post.getUser().getProfilePic())
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(singleImagePostViewHolder.img_user);
                Glide
                        .with(context)
                        .load(post.getMedia().get(0).getPath())
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_place_holder_image)
                        .into(singleImagePostViewHolder.img_media);
                singleImagePostViewHolder.img_media.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showImageViewer(0, post);
                    }
                });

                /***CLICKS****/
                singleImagePostViewHolder.img_post_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onMenuClick(v, post, position);
                        }
                    }
                });
                singleImagePostViewHolder.tv_likes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onLikeDislikeClick(v, post, position, singleImagePostViewHolder.tv_likes);
                        }
                    }
                });
                singleImagePostViewHolder.tv_comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onCommentClick(v, post);
                        }
                    }
                });
                singleImagePostViewHolder.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            if (post.getCommentsCount() > 0)
                                iClicks.onShareClick(v, post);
                        }
                    }
                });
                /************/
                break;
            case 3:
                final MultipleImagePostViewHolder multipleImagePostViewHolder = (MultipleImagePostViewHolder) mainHolder;
                multipleImagePostViewHolder.tv_name.setText(post.getUser().getName());
                multipleImagePostViewHolder.tv_description.setText(post.getBody());
                multipleImagePostViewHolder.tv_likes.setText(String.valueOf(post.getLikesCount()));
                multipleImagePostViewHolder.tv_comments.setText(String.valueOf(post.getCommentsCount()));
                multipleImagePostViewHolder.tv_time_ago.setText(post.getCreatedAt());

                if (post.getIsLiked().toString().equalsIgnoreCase("0"))
                    multipleImagePostViewHolder.tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty_heart, 0, 0, 0);
                else
                    multipleImagePostViewHolder.tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fill_heart, 0, 0, 0);

                Glide
                        .with(context)
                        .load(post.getUser().getProfilePic())
                        .centerCrop()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_dummy)
                        .into(multipleImagePostViewHolder.img_user);
                if (post.getMedia().size() == 2) {
                    multipleImagePostViewHolder.ll_two_images_view.setVisibility(View.VISIBLE);
                    multipleImagePostViewHolder.ll_three_images_view.setVisibility(View.GONE);

                    /***FIRST IMAGE***/
                    Glide
                            .with(context)
                            .load(post.getMedia().get(0).getPath())
                            .centerCrop()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .placeholder(R.drawable.ic_place_holder_image)
                            .into(multipleImagePostViewHolder.ll_2_img_1);
                    multipleImagePostViewHolder.ll_2_img_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImageViewer(0, post);
                        }
                    });
                    /*****************/
                    /***SECOND IMAGE***/
                    Glide
                            .with(context)
                            .load(post.getMedia().get(1).getPath())
                            .centerCrop()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .placeholder(R.drawable.ic_place_holder_image)
                            .into(multipleImagePostViewHolder.ll_2_img_2);
                    multipleImagePostViewHolder.ll_2_img_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImageViewer(1, post);
                        }
                    });
                    /*****************/
                    // multipleImagePostViewHolder.ll_four_more_images_view.setVisibility(View.GONE);
                } else {
                    multipleImagePostViewHolder.ll_two_images_view.setVisibility(View.GONE);
                    multipleImagePostViewHolder.ll_three_images_view.setVisibility(View.VISIBLE);

                    /***FIRST IMAGE***/
                    Glide
                            .with(context)
                            .load(post.getMedia().get(0).getPath())
                            .centerCrop()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .placeholder(R.drawable.ic_place_holder_image)
                            .into(multipleImagePostViewHolder.ll_3_img_1);
                    multipleImagePostViewHolder.ll_3_img_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImageViewer(0, post);
                        }
                    });
                    /*****************/
                    /***SECOND IMAGE***/
                    Glide
                            .with(context)
                            .load(post.getMedia().get(1).getPath())
                            .centerCrop()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .placeholder(R.drawable.ic_place_holder_image)
                            .into(multipleImagePostViewHolder.ll_3_img_2);
                    multipleImagePostViewHolder.ll_3_img_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImageViewer(1, post);
                        }
                    });
                    /*****************/
                    /***THIRD IMAGE***/
                    Glide
                            .with(context)
                            .load(post.getMedia().get(2).getPath())
                            .centerCrop()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .centerCrop()
                            .placeholder(R.drawable.ic_place_holder_image)
                            .into(multipleImagePostViewHolder.ll_3_img_3);
                    multipleImagePostViewHolder.ll_3_img_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showImageViewer(2, post);
                        }
                    });
                    /*****************/
                    int size = post.getMedia().size();
                    if (size - 3 > 0) {
                        multipleImagePostViewHolder.rl_more_images.setVisibility(View.VISIBLE);
                        multipleImagePostViewHolder.tv_remaining_images_count.setText("+" + (size - 3));
                    } else {
                        multipleImagePostViewHolder.rl_more_images.setVisibility(View.GONE);
                    }

                    //multipleImagePostViewHolder.ll_four_more_images_view.setVisibility(View.GONE);
                }
               /* else{
                    multipleImagePostViewHolder.ll_two_images_view.setVisibility(View.GONE);
                    multipleImagePostViewHolder.ll_three_images_view.setVisibility(View.GONE);
                    //multipleImagePostViewHolder.ll_four_more_images_view.setVisibility(View.VISIBLE);
                }*/

                /***CLICKS****/
                multipleImagePostViewHolder.img_post_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onMenuClick(v, post, position);
                        }
                    }
                });
                multipleImagePostViewHolder.tv_likes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onLikeDislikeClick(v, post, position, multipleImagePostViewHolder.tv_likes);
                        }
                    }
                });
                multipleImagePostViewHolder.tv_comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            iClicks.onCommentClick(v, post);
                        }
                    }
                });
                multipleImagePostViewHolder.tv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iClicks != null) {
                            if (post.getCommentsCount() > 0)
                                iClicks.onShareClick(v, post);
                        }
                    }
                });
                /************/
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CommunityViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_likes, tv_comments, tv_share, tv_description, tv_name, tv_time_ago;
        private CircleImageView img_user;
        private ImageView img_post_menu;

        public CommunityViewHolder(View itemView) {
            super(itemView);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_share = itemView.findViewById(R.id.tv_share);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time_ago = itemView.findViewById(R.id.tv_time_ago);

            img_user = itemView.findViewById(R.id.img_user);
            img_post_menu = itemView.findViewById(R.id.img_post_menu);
        }
    }

    public class SingleImagePostViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_likes, tv_comments, tv_share, tv_description, tv_name, tv_time_ago;
        private CircleImageView img_user;
        private ImageView img_post_menu, img_media;

        public SingleImagePostViewHolder(View itemView) {
            super(itemView);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_share = itemView.findViewById(R.id.tv_share);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time_ago = itemView.findViewById(R.id.tv_time_ago);

            img_user = itemView.findViewById(R.id.img_user);
            img_post_menu = itemView.findViewById(R.id.img_post_menu);
            img_media = itemView.findViewById(R.id.img_media);
        }
    }

    public class MultipleImagePostViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_likes, tv_comments, tv_share, tv_description, tv_name, tv_time_ago, tv_remaining_images_count;
        private CircleImageView img_user;
        private ImageView img_post_menu, ll_2_img_1, ll_2_img_2, ll_3_img_1, ll_3_img_2, ll_3_img_3;
        private LinearLayout ll_two_images_view, ll_three_images_view, ll_four_more_images_view;
        private RelativeLayout rl_more_images;

        public MultipleImagePostViewHolder(View itemView) {
            super(itemView);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_share = itemView.findViewById(R.id.tv_share);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time_ago = itemView.findViewById(R.id.tv_time_ago);
            tv_remaining_images_count = itemView.findViewById(R.id.tv_remaining_images_count);

            img_user = itemView.findViewById(R.id.img_user);
            img_post_menu = itemView.findViewById(R.id.img_post_menu);
            ll_2_img_1 = itemView.findViewById(R.id.ll_2_img_1);
            ll_2_img_2 = itemView.findViewById(R.id.ll_2_img_2);
            ll_3_img_1 = itemView.findViewById(R.id.ll_3_img_1);
            ll_3_img_2 = itemView.findViewById(R.id.ll_3_img_2);
            ll_3_img_3 = itemView.findViewById(R.id.ll_3_img_3);

            ll_two_images_view = itemView.findViewById(R.id.ll_two_images_view);
            ll_three_images_view = itemView.findViewById(R.id.ll_three_images_view);
            ll_four_more_images_view = itemView.findViewById(R.id.ll_four_more_images_view);

            rl_more_images = itemView.findViewById(R.id.rl_more_images);
        }
    }

    public class VideoPostViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_likes, tv_comments, tv_share, tv_description, tv_name, tv_time_ago;
        private CircleImageView img_user;
        private ImageView img_post_menu;

        public VideoPostViewHolder(View itemView) {
            super(itemView);
            tv_likes = itemView.findViewById(R.id.tv_likes);
            tv_comments = itemView.findViewById(R.id.tv_comments);
            tv_share = itemView.findViewById(R.id.tv_share);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time_ago = itemView.findViewById(R.id.tv_time_ago);

            img_user = itemView.findViewById(R.id.img_user);
            img_post_menu = itemView.findViewById(R.id.img_post_menu);
        }
    }

    private void showImageViewer(int pos, Post post) {
        new StfalconImageViewer.Builder<>(context, post.getMedia(), new ImageLoader<PostMedia>() {
            @Override
            public void loadImage(ImageView imageView, PostMedia postMedia) {
                Glide.with(context).load(postMedia.getPath()).into(imageView);
            }
        })
                .withStartPosition(pos)
                .show();
    }

}
