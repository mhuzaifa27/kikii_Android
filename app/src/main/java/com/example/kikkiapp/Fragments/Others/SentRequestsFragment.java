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

public class SentRequestsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommunityFragment";
    private Context context=getContext();

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_sent_requests;
    private FriendsAdapter friendsAdapter;
    private List<String> sentRequestsList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sent_requests, container, false);

        initComponents(view);
        loadCommunityPosts();

        return view;
    }

    private void loadCommunityPosts() {
        sentRequestsList.add("posts");
        sentRequestsList.add("posts");
        sentRequestsList.add("posts");
        sentRequestsList.add("posts");
        sentRequestsList.add("posts");
    }

    private void initComponents(View view) {
        rv_sent_requests =view.findViewById(R.id.rv_sent_requests);
        rv_sent_requests.setLayoutManager(new LinearLayoutManager(context));
        rv_sent_requests.setAdapter(new FriendsAdapter(sentRequestsList,getContext()));
    }
    @Override
    public void onRefresh() {

    }
}
