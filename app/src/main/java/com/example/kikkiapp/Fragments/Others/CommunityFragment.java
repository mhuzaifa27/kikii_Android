package com.example.kikkiapp.Fragments.Others;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.kikkiapp.Activities.PostDetailActivity;
import com.example.kikkiapp.Activities.SignUpModule.VerifyOTPActivity;
import com.example.kikkiapp.Adapters.CommunityPostsAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetCommunityPosts;
import com.example.kikkiapp.Callbacks.CallbackSentOTP;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Model.Post;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.PaginationScrollListener;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class CommunityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommunityFragment";
    private Context context;
    private Activity activity;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_no;

    private RecyclerView rv_community_posts;
    private CommunityPostsAdapter communityPostsAdapter;
    private LinearLayoutManager layoutManager;
    private List<Post> communityPostsList = new ArrayList<>();
    private Map<String, String> communityPostsParam = new HashMap<>();
    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetCommunityPosts> callbackGetCommunityPostsCall;
    private CallbackGetCommunityPosts responseAllPosts;

    private Call<CallbackStatus> callbackLikeCall, callbackDisLikeCall;
    private CallbackStatus responseLike, responseDislike;

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

        View view = inflater.inflate(R.layout.fragment_community, container, false);
        initComponents(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv_community_posts.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                //Increment page index to load the next one
               /* if (currentPage != -1)
                    loadCommunityPosts();*/
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        loadCommunityPosts();
    }

    private void loadCommunityPosts() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetCommunityPostsCall = api.getAllPosts(sessionManager.getAccessToken(), String.valueOf(currentPage));
        callbackGetCommunityPostsCall.enqueue(new Callback<CallbackGetCommunityPosts>() {
            @Override
            public void onResponse(Call<CallbackGetCommunityPosts> call, Response<CallbackGetCommunityPosts> response) {
                Log.d(TAG, "onResponse: " + response);
                responseAllPosts = response.body();
                if (responseAllPosts != null) {
                    if (responseAllPosts.getSuccess()) {
                        customLoader.hideIndicator();
                        if (responseAllPosts.getPosts().size() > 0)
                            setData();
                        /*else
                            currentPage = -1;*/
                    } else {
                        Log.d(TAG, "onResponse: " + responseAllPosts.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseAllPosts.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetCommunityPosts> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setData() {
        currentPage = responseAllPosts.getNextOffset();
        communityPostsAdapter = new CommunityPostsAdapter(context);
        communityPostsAdapter.addAll(responseAllPosts.getPosts());
        rv_community_posts.setAdapter(communityPostsAdapter);
        communityPostsAdapter.setOnClickListeners(new CommunityPostsAdapter.IClicks() {
            @Override
            public void onLikeDislikeClick(View view, Post post) {
               /* if (post.equalsIgnoreCase("1")) {
                    likePost(post.getId());
                } else {
                    dislikePost(post.getId());
                }*/
            }
            @Override
            public void onCommentClick(View view, Post post) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", post);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onShareClick(View view, Post post) {

            }
        });
    }

    private void initComponents(View view) {
        context = getContext();
        activity = getActivity();

        customLoader = new CustomLoader(activity, false);
        sessionManager = new SessionManager(context);

        rv_community_posts = view.findViewById(R.id.rv_community_posts);
        layoutManager = new LinearLayoutManager(context);
        rv_community_posts.setLayoutManager(layoutManager);
    }

    private void dislikePost(Integer id) {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackDisLikeCall = api.dislikePost(sessionManager.getAccessToken(), String.valueOf(id));
        callbackDisLikeCall.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                responseDislike = response.body();
                if (responseDislike != null) {
                    if (responseDislike.getSuccess()) {
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseDislike.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "onResponse: " + responseDislike.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseDislike.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackStatus> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void likePost(Integer id) {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackLikeCall = api.likePost(sessionManager.getAccessToken(), String.valueOf(id));
        callbackLikeCall.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                responseLike = response.body();
                if (responseLike != null) {
                    if (responseLike.getSuccess()) {
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseAllPosts.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "onResponse: " + responseAllPosts.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseAllPosts.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackStatus> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    @Override
    public void onRefresh() {

    }
}
