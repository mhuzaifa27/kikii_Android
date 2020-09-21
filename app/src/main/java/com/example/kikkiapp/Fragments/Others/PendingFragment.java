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

public class PendingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommunityFragment";
    private Context context=getContext();

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_pending;
    private FriendsAdapter friendsAdapter;
    private List<String> pendingList =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        initComponents(view);
        loadCommunityPosts();

        return view;
    }
    private void loadCommunityPosts() {
        pendingList.add("posts");
        pendingList.add("posts");
        pendingList.add("posts");
        pendingList.add("posts");
        pendingList.add("posts");
    }
    private void initComponents(View view) {
        rv_pending =view.findViewById(R.id.rv_pending);
        rv_pending.setLayoutManager(new LinearLayoutManager(context));
        rv_pending.setAdapter(new FriendsAdapter(pendingList,getContext()));
    }
    @Override
    public void onRefresh() { }
}
