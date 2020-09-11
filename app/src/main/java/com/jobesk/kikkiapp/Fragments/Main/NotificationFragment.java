package com.jobesk.kikkiapp.Fragments.Main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jobesk.kikkiapp.Adapters.MainChatAdapter;
import com.jobesk.kikkiapp.Adapters.NotificationAdapter;
import com.jobesk.kikkiapp.Adapters.OnlineUsersAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NotificationFragment";
    private Context context;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private NotificationAdapter notificationAdapter;
    private List<String> notificationList=new ArrayList<>();
    private RecyclerView rv_notification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        initComponents(view);

        notificationList.add("chats");
        notificationList.add("chats");
        notificationList.add("chats");
        notificationList.add("chats");

        return view;
    }

    private void initComponents(View view) {
        context=getContext();

        rv_notification=view.findViewById(R.id.rv_notification);
        rv_notification.setLayoutManager(new LinearLayoutManager(context));
        rv_notification.setAdapter(new NotificationAdapter(notificationList,context));
    }

    @Override
    public void onRefresh() {

    }
}
