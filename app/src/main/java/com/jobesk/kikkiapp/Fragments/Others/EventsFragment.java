package com.jobesk.kikkiapp.Fragments.Others;

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

import com.jobesk.kikkiapp.Adapters.CommunityPostsAdapter;
import com.jobesk.kikkiapp.Adapters.EventsAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "EventsFragment";
    private Context context=getContext();

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_events;
    private EventsAdapter eventsAdapter;
    private List<String> eventsList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        initComponents(view);
        loadEvents();

        return view;
    }

    private void loadEvents() {
        eventsList.add("event");
        eventsList.add("event");
        eventsList.add("event");
        eventsList.add("event");
        eventsList.add("event");
    }

    private void initComponents(View view) {
        rv_events =view.findViewById(R.id.rv_events);
        rv_events.setLayoutManager(new LinearLayoutManager(context));
        rv_events.setAdapter(new EventsAdapter(eventsList,getContext()));
    }

    @Override
    public void onRefresh() {

    }
}
