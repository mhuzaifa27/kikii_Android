package com.jobesk.kikkiapp.Fragments.Main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jobesk.kikkiapp.Adapters.MainChatAdapter;
import com.jobesk.kikkiapp.Adapters.OnlineUsersAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ChatFragment";
    private Context context=getContext();

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_online_users,rv_chat_main;
    private OnlineUsersAdapter onlineUsersAdapter;
    private MainChatAdapter mainChatAdapter;

    private List<String> onlineUserList=new ArrayList<>();
    private List<String> mainChatList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        initComponents(view);

        onlineUserList.add("Users");
        onlineUserList.add("Users");
        onlineUserList.add("Users");
        onlineUserList.add("Users");

        mainChatList.add("chats");
        mainChatList.add("chats");
        mainChatList.add("chats");
        mainChatList.add("chats");

        return view;
    }

    private void initComponents(View view) {

        rv_online_users=view.findViewById(R.id.rv_online_users);
        rv_chat_main=view.findViewById(R.id.rv_chat_main);

        rv_online_users.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        rv_online_users.setAdapter(new OnlineUsersAdapter(onlineUserList,context));

        rv_chat_main.setLayoutManager(new LinearLayoutManager(context));
        rv_chat_main.setAdapter(new MainChatAdapter(mainChatList,getContext()));
    }

    @Override
    public void onRefresh() {

    }
}
