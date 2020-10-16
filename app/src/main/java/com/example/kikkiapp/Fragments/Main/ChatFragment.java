package com.example.kikkiapp.Fragments.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.kikkiapp.Adapters.MainChatAdapter;
import com.example.kikkiapp.Adapters.OnlineUsersAdapter;
import com.example.kikkiapp.Adapters.YourMatchAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetConversations;
import com.example.kikkiapp.Callbacks.CallbackGetMatch;
import com.example.kikkiapp.Model.Conversation;
import com.example.kikkiapp.Model.OnlineUser;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;

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
    private OnlineUsersAdapter onlineUsersAdapter;
    private MainChatAdapter mainChatAdapter;

    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetConversations> callbackGetConversationsCall;
    private CallbackGetConversations responseGetConversations;

    private List<OnlineUser> onlineUserList=new ArrayList<>();
    private List<Conversation> mainChatList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        initComponents(view);
        loadConversations();

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    private void loadConversations() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetConversationsCall = api.getConversations(sessionManager.getAccessToken());
        callbackGetConversationsCall.enqueue(new Callback<CallbackGetConversations>() {
            @Override
            public void onResponse(Call<CallbackGetConversations> call, Response<CallbackGetConversations> response) {
                Log.d(TAG, "onResponse: " + response);
                responseGetConversations = response.body();
                if (responseGetConversations != null) {
                    if (responseGetConversations.getSuccess()) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (responseGetConversations.getOnlineUsers().size() > 0){
                            rv_online_users.setVisibility(View.VISIBLE);
                            setOnlineUsers();
                        }else{
                            rv_online_users.setVisibility(View.GONE);
                        }
                        if (responseGetConversations.getConversations().size() > 0){
                            tv_no.setVisibility(View.GONE);
                            rv_chat_main.setVisibility(View.VISIBLE);
                            setConversations();
                        }else{
                            customLoader.hideIndicator();
                            tv_no.setVisibility(View.VISIBLE);
                            rv_chat_main.setVisibility(View.GONE);
                        }

                        /*else
                            currentPage = -1;*/
                    } else {
                        Log.d(TAG, "onResponse: " + responseGetConversations.getMessage());
                        Toast.makeText(context, responseGetConversations.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetConversations> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setOnlineUsers() {
        onlineUserList=responseGetConversations.getOnlineUsers();
        rv_online_users.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        rv_online_users.setAdapter(new OnlineUsersAdapter(onlineUserList,context));
    }

    private void setConversations() {
        mainChatList=responseGetConversations.getConversations();
        rv_chat_main.setLayoutManager(new LinearLayoutManager(context));
        rv_chat_main.setAdapter(new MainChatAdapter(mainChatList,context));
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

        tv_no=view.findViewById(R.id.tv_no);


    }

    @Override
    public void onRefresh() {
        mainChatList.clear();
        onlineUserList.clear();
        loadConversations();
    }
}
