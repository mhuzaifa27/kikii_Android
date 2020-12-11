package com.example.kikkiapp.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Activities.ChattingActivity;
import com.example.kikkiapp.Activities.SingleMessagingActivity;
import com.example.kikkiapp.Firebase.AppState;
import com.example.kikkiapp.Firebase.ChangeEventListener;
import com.example.kikkiapp.Firebase.Model.FirebaseUserModel;
import com.example.kikkiapp.Firebase.Model.InboxItem;
import com.example.kikkiapp.Firebase.Services.LastMessageService;
import com.example.kikkiapp.Firebase.Services.SingleMessagingService;
import com.example.kikkiapp.Firebase.Services.UserService;
import com.example.kikkiapp.Model.Conversation;
import com.example.kikkiapp.Netwrok.Constants;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.TimeUtils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainChatAdapter extends RecyclerView.Adapter<MainChatAdapter.TravelBuddyViewHolder> {
    private List<InboxItem> data;
    private Context context;
    private boolean isLoadingAdded;
    private FirebaseUserModel user = null;
    private UserService userService;
    private SingleMessagingService singleMessagingService;
    private LastMessageService lastMessageService;

    public MainChatAdapter(List<InboxItem> data, Context context) {
        this.data = data;
        this.context = context;
        userService = new UserService();
        userService.setOnChangedListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(EventType type, int index, int oldIndex) {

            }

            @Override
            public void onDataChanged() {
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void add(InboxItem mc) {
        data.add(0, mc);
        if (data.size() > 1)
            notifyItemInserted(0);
        notifyDataSetChanged();
    }

    public void addAll(List<InboxItem> mcList) {
        data = mcList;
        notifyDataSetChanged();
    }

    public void remove(InboxItem city) {
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
        add(new InboxItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        int position = data.size() - 1;
        InboxItem item = getItem(position);
        if (item != null) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public InboxItem
    getItem(int position) {
        return data.get(position);
    }

    @Override
    public TravelBuddyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_main_chat, parent, false);
        return new TravelBuddyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TravelBuddyViewHolder holder, int position) {
        final InboxItem inboxItem = data.get(position);
        LastMessageService lastMessageService = new LastMessageService(AppState.currentFireUser.getUid(), inboxItem.getUserId());
        lastMessageService.setOnChangedListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(EventType type, int index, int oldIndex) {
            }

            @Override
            public void onDataChanged() {
                if (lastMessageService.getCount() > 0) {
                    holder.tv_date.setText(TimeUtils.getTime((long) lastMessageService.snapshotForKey("time").getValue()));
                    String type = lastMessageService.snapshotForKey("type").getValue().toString();
                    if (type.equalsIgnoreCase(Constants.TYPE_TEXT))
                        holder.tv_last_message.setText(lastMessageService.snapshotForKey("message").getValue().toString());
                    else if (type.equalsIgnoreCase(Constants.TYPE_AUDIO)) {
                        holder.tv_last_message.setText("Audio");
                    } else if (type.equalsIgnoreCase(Constants.IMAGE)) {
                        holder.tv_last_message.setText("Image");
                    } else {
                        holder.tv_last_message.setText("Video");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        SingleMessagingService singleMessagingService = new SingleMessagingService(AppState.currentFireUser.getUid(), inboxItem.getUserId());
        singleMessagingService.setOnChangedListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(EventType type, int index, int oldIndex) { }
            @Override
            public void onDataChanged() { }
            @Override
            public void onCancelled(DatabaseError error) { }
        });
        Glide
                .with(context)
                .load(inboxItem.getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(holder.img_user);
        holder.tv_name.setText(inboxItem.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userService.getUserById(inboxItem.getUserId());
                Intent intent = new Intent(context, SingleMessagingActivity.class);
                intent.putExtra(Constants.ID, inboxItem.getUserId());
                intent.putExtra(Constants.START_NAME, inboxItem.getName());
                intent.putExtra(Constants.IMAGE, inboxItem.getProfilePic());
                intent.putExtra(Constants.CREATE_RIDE_OBJ, user);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog alertDialog = new Dialog(context);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.dialogue_delete_conversation);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.findViewById(R.id.tv_btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.findViewById(R.id.tv_btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(singleMessagingService!=null){
                            singleMessagingService.deleteConversation(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if(lastMessageService!=null){
                                        lastMessageService.deleteConversation(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                alertDialog.show();
                return true;
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

        public TravelBuddyViewHolder(View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_user);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_last_message = itemView.findViewById(R.id.tv_last_message);
            tv_date = itemView.findViewById(R.id.tv_date);

        }
    }
}
