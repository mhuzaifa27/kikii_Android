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

public class PendingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "PendingFragment";
    private Context context;
    private Activity activity;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_pending;
    private FriendsAdapter friendsAdapter;
    private List<FellowUser> pendingList =new ArrayList<>();
    private LinearLayoutManager layoutManager;

    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetFellowUsers> callbackGetPendingRequestsCall;
    private CallbackGetFellowUsers responsePendingRequests;
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

        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        initComponents(view);
        loadPendingRequests();

        return view;
    }
    private void loadPendingRequests() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadEvents: " + sessionManager.getAccessToken());
        callbackGetPendingRequestsCall = api.getPendingRequests(sessionManager.getAccessToken(), String.valueOf(0));
        callbackGetPendingRequestsCall.enqueue(new Callback<CallbackGetFellowUsers>() {
            @Override
            public void onResponse(Call<CallbackGetFellowUsers> call, Response<CallbackGetFellowUsers> response) {
                Log.d(TAG, "onResponse: " + response);
                responsePendingRequests = response.body();
                if (responsePendingRequests != null) {
                    if (responsePendingRequests.getSuccess()) {
                        customLoader.hideIndicator();
                        swipeRefreshLayout.setRefreshing(false);
                        if (responsePendingRequests.getFellowUsers().size() > 0){
                            tv_no.setVisibility(View.GONE);
                            rv_pending.setVisibility(View.VISIBLE);
                            setData();
                        }
                        else{
                            tv_no.setVisibility(View.VISIBLE);
                            rv_pending.setVisibility(View.GONE);
                        }
                        /*else
                            currentPage = -1;*/
                    } else {
                        Log.d(TAG, "onResponse: " + responsePendingRequests.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responsePendingRequests.getMessage(), Toast.LENGTH_SHORT).show();
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
        currentPage = responsePendingRequests.getNextOffset();
        pendingList = responsePendingRequests.getFellowUsers();
        friendsAdapter.addAll(pendingList);
        rv_pending.setAdapter(friendsAdapter);

    }

    private void initComponents(View view) {
        context=getContext();
        activity=getActivity();

        customLoader=new CustomLoader(activity,false);
        sessionManager=new SessionManager(context);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout);

        rv_pending =view.findViewById(R.id.rv_pending);
        layoutManager=new LinearLayoutManager(context);
        rv_pending.setLayoutManager(layoutManager);
        friendsAdapter=new FriendsAdapter("pendings",pendingList,getContext());
        rv_pending.setAdapter(friendsAdapter);

        tv_no=view.findViewById(R.id.tv_no);
    }
    @Override
    public void onRefresh() { }
}
