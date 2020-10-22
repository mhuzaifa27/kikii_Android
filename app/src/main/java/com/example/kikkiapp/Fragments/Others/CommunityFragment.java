package com.example.kikkiapp.Fragments.Others;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
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
import com.example.kikkiapp.Adapters.CommunityPostsAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetCommunityPosts;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Model.Post;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.PaginationScrollListener;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.example.kikkiapp.Utils.ShowPopupMenus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CommunityFragment";
    public static boolean NEED_TO_LOAD_DATA = true;
    public static final int REQUEST_POST_DETAIL=245;
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

    private Call<CallbackStatus> callbackLikeCall, callbackDeletePost;
    private CallbackStatus responseLike, responseDeletePost;

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
        //if (NEED_TO_LOAD_DATA)
            loadCommunityPosts();
        swipeRefreshLayout.setOnRefreshListener(this);
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
    }

    private void loadCommunityPosts() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetCommunityPostsCall = api.getAllPosts(sessionManager.getAccessToken(), String.valueOf(0));
        callbackGetCommunityPostsCall.enqueue(new Callback<CallbackGetCommunityPosts>() {
            @Override
            public void onResponse(Call<CallbackGetCommunityPosts> call, Response<CallbackGetCommunityPosts> response) {
                Log.d(TAG, "onResponse: " + response);
                responseAllPosts = response.body();
                if (responseAllPosts != null) {
                    if (responseAllPosts.getSuccess()) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (responseAllPosts.getPosts().size() > 0){
                            tv_no.setVisibility(View.GONE);
                            rv_community_posts.setVisibility(View.VISIBLE);
                            setData();
                        }else{
                            customLoader.hideIndicator();
                            tv_no.setVisibility(View.VISIBLE);
                            rv_community_posts.setVisibility(View.GONE);
                        }
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
        NEED_TO_LOAD_DATA = false;
        currentPage = responseAllPosts.getNextOffset();
        communityPostsList = responseAllPosts.getPosts();
        communityPostsAdapter.addAll(communityPostsList);
        rv_community_posts.setAdapter(communityPostsAdapter);
        customLoader.hideIndicator();
        communityPostsAdapter.setOnClickListeners(new CommunityPostsAdapter.IClicks() {
            @Override
            public void onLikeDislikeClick(View view, Post post, int position, TextView tv_likes) {
                likeDislikePost(post.getId(), position, tv_likes);
            }

            @Override
            public void onCommentClick(View view, Post post) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post", post);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_POST_DETAIL);
            }

            @Override
            public void onShareClick(View view, Post post) {
            }

            @Override
            public void onMenuClick(View view, final Post post, final int position) {
                ShowPopupMenus.showPostMenu(activity,view, post,position,rv_community_posts,communityPostsList,communityPostsAdapter);
            }
        });
    }

    private void initComponents(View view) {
        context = getContext();
        activity = getActivity();

        customLoader = new CustomLoader(activity, false);
        sessionManager = new SessionManager(context);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh_layout);
        communityPostsAdapter = new CommunityPostsAdapter(context);
        communityPostsAdapter.addAll(communityPostsList);

        rv_community_posts = view.findViewById(R.id.rv_community_posts);
        layoutManager = new LinearLayoutManager(context);
        rv_community_posts.setLayoutManager(layoutManager);

        tv_no=view.findViewById(R.id.tv_no);
    }

    private void likeDislikePost(Integer id, final int position, final TextView tv_likes) {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackLikeCall = api.likeDislikePost(sessionManager.getAccessToken(), String.valueOf(id));
        callbackLikeCall.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                responseLike = response.body();
                if (responseLike != null) {
                    if (responseLike.getSuccess()) {
                        customLoader.hideIndicator();
                        Post post = communityPostsList.get(position);
                        int likeCount = post.getLikesCount();
                        if (post.getIsLiked().toString().equalsIgnoreCase("0")) {
                            likeCount = likeCount + 1;
                            post.setLikesCount(likeCount);
                            post.setIsLiked(1);
                            tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fill_heart, 0, 0, 0);
                        } else {
                            likeCount = likeCount - 1;
                            post.setLikesCount(likeCount);
                            post.setIsLiked(0);
                            tv_likes.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_empty_heart, 0, 0, 0);
                        }
                        communityPostsList.set(position, post);
                        communityPostsAdapter.notifyItemChanged(position, post);
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
        communityPostsList.clear();
        loadCommunityPosts();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_POST_DETAIL && resultCode==Activity.RESULT_OK){
            loadCommunityPosts();
        }
    }
}
