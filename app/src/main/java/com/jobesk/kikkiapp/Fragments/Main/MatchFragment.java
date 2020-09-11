package com.jobesk.kikkiapp.Fragments.Main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jobesk.kikkiapp.Adapters.YourMatchAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MatchFragment";
    private Context context=getContext();
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;
    private RecyclerView rv_your_matches;
    private YourMatchAdapter yourMatchAdapter;
    private List<String> yourMatchesList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_match, container, false);

        initComponents(view);
        yourMatchesList.add("Jerry Kazz");
        yourMatchesList.add("Jerry Kazz");
        yourMatchesList.add("Jerry Kazz");

        return view;
    }

    private void initComponents(View view) {
        rv_your_matches=view.findViewById(R.id.rv_your_matches);
        rv_your_matches.setLayoutManager(new GridLayoutManager(context,2));
        rv_your_matches.setAdapter(new YourMatchAdapter(yourMatchesList,context));
    }

    @Override
    public void onRefresh() {

    }
}
