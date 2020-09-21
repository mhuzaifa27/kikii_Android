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

import com.example.kikkiapp.Adapters.CommunityPostsAdapter;
import com.example.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommunityFragment";
    private Context context=getContext();

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_community_posts;
    private CommunityPostsAdapter communityPostsAdapter;
    private List<String> communityPostsList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, container, false);

        initComponents(view);
        loadCommunityPosts();

        return view;
    }

    private void loadCommunityPosts() {
        communityPostsList.add("posts");
        communityPostsList.add("posts");
        communityPostsList.add("posts");
        communityPostsList.add("posts");
        communityPostsList.add("posts");
    }

    private void initComponents(View view) {
        rv_community_posts=view.findViewById(R.id.rv_community_posts);
        rv_community_posts.setLayoutManager(new LinearLayoutManager(context));
        rv_community_posts.setAdapter(new CommunityPostsAdapter(communityPostsList,getContext()));
    }

    @Override
    public void onRefresh() {

    }
}
