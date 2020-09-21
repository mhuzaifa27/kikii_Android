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

import com.example.kikkiapp.Adapters.KikiiPostsAdapter;
import com.example.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class KikiiFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "KikiiFragment";
    private Context context=getContext();

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_kikii_posts;
    private KikiiPostsAdapter kikiiPostsAdapter;
    private List<String> kikiiPostsList =new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kikii, container, false);

        initComponents(view);
        loadEvents();

        return view;
    }

    private void loadEvents() {
        kikiiPostsList.add("event");
        kikiiPostsList.add("event");
        kikiiPostsList.add("event");
        kikiiPostsList.add("event");
        kikiiPostsList.add("event");
    }

    private void initComponents(View view) {
        rv_kikii_posts =view.findViewById(R.id.rv_kikii_posts);
        rv_kikii_posts.setLayoutManager(new LinearLayoutManager(context));
        rv_kikii_posts.setAdapter(new KikiiPostsAdapter(kikiiPostsList,context));
    }

    @Override
    public void onRefresh() {

    }
}
