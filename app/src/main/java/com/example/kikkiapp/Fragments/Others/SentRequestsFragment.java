package com.example.kikkiapp.Fragments.Others;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.kikkiapp.Adapters.FriendsAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetFellowUsers;
import com.example.kikkiapp.Model.FellowUser;
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

public class SentRequestsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommunityFragment";
    private Context context;
    private Activity activity;


    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_sent_requests;
    private FriendsAdapter friendsAdapter;
    private List<FellowUser> sentRequestsList =new ArrayList<>();
    private LinearLayoutManager layoutManager;

    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetFellowUsers> callbackGetSentRequests;
    private CallbackGetFellowUsers responseSentRequests;
    /*****/
    ProgressBar progressBar;

    // Index from which pagination should start (0 is 1st page in our case)
    private static final int PAGE_START = 0;
    // Indicates if footer ProgressBar is shown (i.e. next page is loading)
    private boolean isLoading = false;
    // If current page is the last page (Pagination will stop after this page load)
    private boolean isLastPage = false;
    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 3;
    // indicates the current page which Pagination is fetching.
    private int currentPage = PAGE_START;

    /*****/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sent_requests, container, false);

        initComponents(view);
        loadSentRequests();

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }
    private void loadSentRequests() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadSentRequests: " + sessionManager.getAccessToken());
        callbackGetSentRequests = api.getSentRequests(sessionManager.getAccessToken(), String.valueOf(0));
        callbackGetSentRequests.enqueue(new Callback<CallbackGetFellowUsers>() {
            @Override
            public void onResponse(Call<CallbackGetFellowUsers> call, Response<CallbackGetFellowUsers> response) {
                Log.d(TAG, "onResponse: " + response);
                responseSentRequests = response.body();
                if (responseSentRequests != null) {
                    if (responseSentRequests.getSuccess()) {
                        customLoader.hideIndicator();
                        swipeRefreshLayout.setRefreshing(false);
                        if (responseSentRequests.getFellowUsers().size() > 0){
                            tv_no.setVisibility(View.GONE);
                            rv_sent_requests.setVisibility(View.VISIBLE);
                            setData();
                        }
                        else{
                            tv_no.setVisibility(View.VISIBLE);
                            rv_sent_requests.setVisibility(View.GONE);
                        }
                        /*else
                            currentPage = -1;*/
                    } else {
                        Log.d(TAG, "onResponse: " + responseSentRequests.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseSentRequests.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetFellowUsers> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setData() {
        currentPage = responseSentRequests.getNextOffset();
        sentRequestsList = responseSentRequests.getFellowUsers();
        friendsAdapter.addAll(sentRequestsList);
        rv_sent_requests.setAdapter(friendsAdapter);

    }

    private void initComponents(View view) {
        context=getContext();
        activity=getActivity();

        customLoader=new CustomLoader(activity,false);
        sessionManager=new SessionManager(context);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout);

        rv_sent_requests =view.findViewById(R.id.rv_sent_requests);
        layoutManager=new LinearLayoutManager(context);
        rv_sent_requests.setLayoutManager(layoutManager);
        friendsAdapter=new FriendsAdapter("requests",sentRequestsList,getContext());
        rv_sent_requests.setAdapter(friendsAdapter);

        tv_no=view.findViewById(R.id.tv_no);

    }
    @Override
    public void onRefresh() {
        loadSentRequests(); }
}
