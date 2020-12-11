package com.example.kikkiapp.Fragments.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.kikkiapp.Adapters.MainChatAdapter;
import com.example.kikkiapp.Adapters.OnlineUsersAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetOnlineUsers;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Firebase.AppState;
import com.example.kikkiapp.Firebase.ChangeEventListener;
import com.example.kikkiapp.Firebase.Model.FirebaseUserModel;
import com.example.kikkiapp.Firebase.Model.InboxItem;
import com.example.kikkiapp.Firebase.Model.Message;
import com.example.kikkiapp.Firebase.Services.InboxService;
import com.example.kikkiapp.Firebase.Services.UserService;
import com.example.kikkiapp.Model.OnlineUser;
import com.example.kikkiapp.Model.User;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ChatFragment";
    private Context context;
    private Activity activity;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_online_users,rv_chat_main;
    private ItemTouchHelper itemTouchHelper;
    private OnlineUsersAdapter onlineUsersAdapter;
    private MainChatAdapter mainChatAdapter;

    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetOnlineUsers> callbackGetOnlineUsersCall;
    private CallbackGetOnlineUsers responseGetOnlineUsers;
    private Call<CallbackStatus> callbackStatusCall;
    private CallbackStatus responseStatus;

    private List<User> onlineUserList=new ArrayList<>();
    private List<InboxItem> mainChatList=new ArrayList<>();

    private InboxService inboxService;
    private UserService userService;
    private ImageView img_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        initComponents(view);
        if(sessionManager.getProfileUser().getUpgraded().toString().equalsIgnoreCase("1")){
            loadConversations();
        }
        else{
            rv_online_users.setVisibility(View.GONE);
        }

        /*ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        if(direction==ItemTouchHelper.LEFT){
                            Conversation conversation=mainChatList.get(viewHolder.getAdapterPosition());
                            int position=viewHolder.getAdapterPosition();
                            deleteConversation(conversation.getId(),position);
                        }
                    }
                };
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv_chat_main);*/

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
        inboxService = new InboxService();
        inboxService.setOnChangedListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(EventType type, int index, int oldIndex) {

            }

            @Override
            public void onDataChanged() {
                //progressHUD.show();
                mainChatList.clear();
                if (inboxService.getCount() > 0) {
                    for (int i = 0; i < inboxService.getCount(); i++) {
                        DataSnapshot sp = inboxService.getItem(i);
                        if (sp.getChildrenCount() > 0) {
                            for (int j = 0; j < sp.getChildrenCount(); j++) {
                                String map = sp.getKey();
                                if (map.contains("___")) {
                                    String userId = map.split("___")[0];
                                    if (userId.equalsIgnoreCase(AppState.currentBpackCustomer.getUserId())) {
                                        FirebaseUserModel user = userService.getUserById(map.split("___")[1]);
                                        Message message = sp.getValue(Message.class);
                                        mainChatList.add(new InboxItem("2", user.getUserName(), user.getImage(), message.getTime(), message.getMessage(), user.getUserId()));
                                        break;
                                    }
                                }
                                /*******WORKING CODE FOR GROUP CHAT********/
                                /*else {
                                    Group group=groupService.getGroupById(map);
                                    List<String> members=group.getMembersList();
                                    if(members.contains(AppState.currentFireUser.getUid()) || group.getAdmin().equalsIgnoreCase(AppState.currentFireUser.getUid())){
                                        //User user = userService.getUserById(map);
                                        Message message = sp.getValue(Message.class);
                                        inboxList.add(new InboxItem("2", group.getName(), "Group", message.getTime(), message.getMessage(), group.getGroupId()));
                                        break;
                                    }
                                }*/
                                /*******WORKING CODE FOR GROUP CHAT********/
                            }
                            mainChatAdapter.addAll(mainChatList);
                            //progressHUD.dismiss();
                        }
                    }
                    if(mainChatList.size()==0){
                        tv_no.setVisibility(View.VISIBLE);
                        rv_chat_main.setVisibility(View.GONE);
                    }
                }
                //progressHUD.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    private void loadConversations() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetOnlineUsersCall = api.getOnlineUsers(sessionManager.getAccessToken());
        callbackGetOnlineUsersCall.enqueue(new Callback<CallbackGetOnlineUsers>() {
            @Override
            public void onResponse(Call<CallbackGetOnlineUsers> call, Response<CallbackGetOnlineUsers> response) {
                Log.d(TAG, "onResponse: " + response);
                responseGetOnlineUsers = response.body();
                if (responseGetOnlineUsers != null) {
                    if (responseGetOnlineUsers.getSuccess()) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (responseGetOnlineUsers.getOnlineUsers().size() > 0){
                            rv_online_users.setVisibility(View.VISIBLE);
                            setOnlineUsers();
                        }else{
                            customLoader.hideIndicator();
                            rv_online_users.setVisibility(View.GONE);
                        }
                    } else {
                        customLoader.hideIndicator();
                        Log.d(TAG, "onResponse: ERROR");
                        //Toast.makeText(context, responseGetOnlineUsers.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetOnlineUsers> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setOnlineUsers() {
        onlineUserList= responseGetOnlineUsers.getOnlineUsers();
        rv_online_users.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        rv_online_users.setAdapter(new OnlineUsersAdapter(onlineUserList,context));
        customLoader.hideIndicator();
    }

    private void initComponents(View view) {
        context=getContext();
        activity=getActivity();

        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout);
        customLoader=new CustomLoader(activity,false);
        sessionManager=new SessionManager(context);

        rv_online_users=view.findViewById(R.id.rv_online_users);
        rv_chat_main=view.findViewById(R.id.rv_chat_main);

        rv_chat_main.setLayoutManager(new LinearLayoutManager(context));
        mainChatAdapter=new MainChatAdapter(mainChatList,context);
        rv_chat_main.setAdapter(mainChatAdapter);

        rv_online_users.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        onlineUsersAdapter=new OnlineUsersAdapter(onlineUserList,context);
        rv_online_users.setAdapter(onlineUsersAdapter);

        tv_no=view.findViewById(R.id.tv_no);

        img_add=view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);

    }

    @Override
    public void onRefresh() {
        mainChatList.clear();
        onlineUserList.clear();
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
        inboxService = new InboxService();
        inboxService.setOnChangedListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(EventType type, int index, int oldIndex) {

            }

            @Override
            public void onDataChanged() {
                //progressHUD.show();
                mainChatList.clear();
                if (inboxService.getCount() > 0) {
                    for (int i = 0; i < inboxService.getCount(); i++) {
                        DataSnapshot sp = inboxService.getItem(i);
                        if (sp.getChildrenCount() > 0) {
                            for (int j = 0; j < sp.getChildrenCount(); j++) {
                                String map = sp.getKey();
                                if (map.contains("___")) {
                                    String userId = map.split("___")[0];
                                    if (userId.equalsIgnoreCase(AppState.currentBpackCustomer.getUserId())) {
                                        FirebaseUserModel user = userService.getUserById(map.split("___")[1]);
                                        Message message = sp.getValue(Message.class);
                                        mainChatList.add(new InboxItem("2", user.getUserName(), user.getImage(), message.getTime(), message.getMessage(), user.getUserId()));
                                        break;
                                    }
                                }
                                /*******WORKING CODE FOR GROUP CHAT********/
                                /*else {
                                    Group group=groupService.getGroupById(map);
                                    List<String> members=group.getMembersList();
                                    if(members.contains(AppState.currentFireUser.getUid()) || group.getAdmin().equalsIgnoreCase(AppState.currentFireUser.getUid())){
                                        //User user = userService.getUserById(map);
                                        Message message = sp.getValue(Message.class);
                                        inboxList.add(new InboxItem("2", group.getName(), "Group", message.getTime(), message.getMessage(), group.getGroupId()));
                                        break;
                                    }
                                }*/
                                /*******WORKING CODE FOR GROUP CHAT********/
                            }
                            mainChatAdapter.addAll(mainChatList);
                            //progressHUD.dismiss();
                        }
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
                //progressHUD.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    private void deleteConversation(Integer id, final int position) {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackStatusCall = api.deleteConversation(String.valueOf(id),sessionManager.getAccessToken());
        callbackStatusCall.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                responseStatus = response.body();
                if (responseStatus != null) {
                    if (responseStatus.getSuccess()) {
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseStatus.getMessage(), Toast.LENGTH_SHORT).show();
                        mainChatList.remove(position);
                        mainChatAdapter.notifyItemRemoved(position);
                    } else {
                        Log.d(TAG, "onResponse: " + responseStatus.getMessage());
                        Toast.makeText(context, responseStatus.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackStatus> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }
}
