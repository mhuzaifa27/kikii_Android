package com.example.kikkiapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Adapters.CommentsAdapter;
import com.example.kikkiapp.Adapters.CommunityPostsAdapter;
import com.example.kikkiapp.Callbacks.CallbackAddComment;
import com.example.kikkiapp.Callbacks.CallbackGetCommunityPosts;
import com.example.kikkiapp.Callbacks.CallbackGetPostComments;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Model.Post;
import com.example.kikkiapp.Model.PostComment;
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

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChattingActivity";
    private Context context = PostDetailActivity.this;
    private Activity activity = PostDetailActivity.this;

    private RecyclerView rv_comments;
    private CommentsAdapter commentsAdapter;
    private LinearLayoutManager layoutManager;
    private List<PostComment> commentsList = new ArrayList<>();
    private Post post;
    private TextView tv_name, tv_description;
    private CircleImageView img_user;
    private ImageView img_back, img_send;

    private CustomLoader customLoader;
    private SessionManager sessionManager;
    private Map<String, String> addCommentParams = new HashMap<>();

    private Call<CallbackGetPostComments> callbackGetPostCommentsCall;
    private CallbackGetPostComments responsePostComments;

    private Call<CallbackStatus> callbackDeleteComment;
    private CallbackStatus responseDeleteComment;

    private Call<CallbackAddComment> callbackAddCommentCall;
    private CallbackAddComment responseAddComment;

    private EditText et_comment;
    private String comment;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        initComponents();
        getIntentData();
        setPostData();
        loadComments();

        img_send.setOnClickListener(this);

        rv_comments.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                //Increment page index to load the next one
                /*if (currentPage != -1)
                    loadComments();*/
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

    private void setPostData() {
        tv_name.setText(post.getUser().getName());
        tv_description.setText(post.getBody());
        Glide
                .with(context)
                .load(post.getUser().getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(img_user);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        post = (Post) bundle.getSerializable("post");
    }

    private void loadComments() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetPostCommentsCall = api.getPostComments(sessionManager.getAccessToken(), String.valueOf(post.getId()));
        callbackGetPostCommentsCall.enqueue(new Callback<CallbackGetPostComments>() {
            @Override
            public void onResponse(Call<CallbackGetPostComments> call, Response<CallbackGetPostComments> response) {
                Log.d(TAG, "onResponse: " + response);
                responsePostComments = response.body();
                if (responsePostComments != null) {
                    if (responsePostComments.getSuccess()) {
                        customLoader.hideIndicator();
                        if (responsePostComments.getComments().size() > 0)
                            setData();
                        /*else
                            currentPage = -1;*/
                    } else {
                        Log.d(TAG, "onResponse: " + responsePostComments.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responsePostComments.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetPostComments> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setData() {
        currentPage = responsePostComments.getNextOffset();
        commentsAdapter = new CommentsAdapter(activity);
        commentsAdapter.addAll(responsePostComments.getComments());
        rv_comments.setAdapter(commentsAdapter);
        commentsAdapter.setOnClickListeners(new CommentsAdapter.IClicks() {
            @Override
            public void onMenuClick(View view, PostComment postComment, final int position) {
                PopupMenu popup = new PopupMenu(activity, view);
                //Inflating the Popup using xml file
                if(postComment.getUserId().toString().equalsIgnoreCase(sessionManager.getUserID())){
                    popup.getMenuInflater()
                            .inflate(R.menu.comment_menu_1, popup.getMenu());
                }
                else{
                    popup.getMenuInflater()
                            .inflate(R.menu.comment_menu_2, popup.getMenu());
                }
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_delete:
                                deleteComment(post.getId(), position);
                                break;
                            case R.id.menu_report:

                                break;
                        }
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });
    }

    private void deleteComment(Integer id, final int position) {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "deleteComment: " + sessionManager.getAccessToken());
        callbackDeleteComment = api.deleteComment(String.valueOf(id), sessionManager.getAccessToken());
        callbackDeleteComment.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                customLoader.hideIndicator();
                responseDeleteComment = response.body();
                if (responseDeleteComment != null) {
                    if (responseDeleteComment.getSuccess()) {
                        customLoader.hideIndicator();
                        commentsList.remove(position);
                        commentsAdapter.notifyItemRemoved(position);
                    } else {
                        Log.d(TAG, "onResponse: " + responseDeleteComment.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseDeleteComment.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void initComponents() {
        customLoader = new CustomLoader(this, false);
        sessionManager = new SessionManager(this);

        rv_comments = findViewById(R.id.rv_comments);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setReverseLayout(true);
        rv_comments.setLayoutManager(layoutManager);

        tv_name = findViewById(R.id.tv_name);
        tv_description = findViewById(R.id.tv_description);

        img_user = findViewById(R.id.img_user);
        img_back = findViewById(R.id.img_back);
        img_send = findViewById(R.id.img_send);

        et_comment = findViewById(R.id.et_comment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_send:
                if (et_comment.getText().toString().isEmpty()) {
                    et_comment.setError(getResources().getString(R.string.et_error));
                } else {
                    comment = et_comment.getText().toString();

                    addCommentParams.put(Constant.BODY, comment);
                    addCommentParams.put(Constant.POST_ID, String.valueOf(post.getId()));

                    addComment();
                }
                break;
        }
    }

    private void addComment() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackAddCommentCall = api.addComment(sessionManager.getAccessToken(), addCommentParams);
        callbackAddCommentCall.enqueue(new Callback<CallbackAddComment>() {
            @Override
            public void onResponse(Call<CallbackAddComment> call, Response<CallbackAddComment> response) {
                Log.d(TAG, "onResponse: " + response);
                responseAddComment = response.body();
                if (responseAddComment != null) {
                    if (responseAddComment.getSuccess()) {
                        customLoader.hideIndicator();
                        commentsAdapter.add(responseAddComment.getComment());
                        et_comment.setText("");
                        View view = PostDetailActivity.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + responseAddComment.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseAddComment.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackAddComment> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }
}
