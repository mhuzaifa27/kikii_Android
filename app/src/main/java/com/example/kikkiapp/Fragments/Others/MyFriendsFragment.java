package com.example.kikkiapp.Fragments.Others;

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

import com.example.kikkiapp.Adapters.FriendsAdapter;
import com.example.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommunityFragment";
    private Context context=getContext();

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_my_friends;
    private FriendsAdapter friendsAdapter;
    private List<String> myFriendsList =new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);

        initComponents(view);
        loadCommunityPosts();

        return view;
    }

    private void loadCommunityPosts() {
        myFriendsList.add("posts");
        myFriendsList.add("posts");
        myFriendsList.add("posts");
        myFriendsList.add("posts");
        myFriendsList.add("posts");
    }

    private void initComponents(View view) {
        rv_my_friends =view.findViewById(R.id.rv_my_friends);
        rv_my_friends.setLayoutManager(new LinearLayoutManager(context));
        rv_my_friends.setAdapter(new FriendsAdapter(myFriendsList,getContext()));
    }

    @Override
    public void onRefresh() {

    }
}
