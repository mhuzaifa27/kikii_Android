package com.example.kikkiapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kikkiapp.Adapters.CommentsAdapter;
import com.example.kikkiapp.Callbacks.CallbackAddComment;
import com.example.kikkiapp.Callbacks.CallbackGetPostComments;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Model.KikiiPost;
import com.example.kikkiapp.Model.PostComment;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constants;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CommonMethods;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.PaginationScrollListener;
import com.example.kikkiapp.Utils.SelectImage;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.example.kikkiapp.Utils.ShowPopupMenus;
import com.example.kikkiapp.Utils.ShowSelectImageBottomSheet;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KikiiPostDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "KikiiPostDetailActivity";
    private Context context = KikiiPostDetailActivity.this;
    private Activity activity = KikiiPostDetailActivity.this;

    private RecyclerView rv_comments;
    private CommentsAdapter commentsAdapter;
    private LinearLayoutManager layoutManager;
    private List<PostComment> commentsList = new ArrayList<>();
    private KikiiPost post;
    private TextView tv_name, tv_description,tv_no;
    private CircleImageView img_user;
    private ImageView img_back, img_send,img_post_menu, img_select_media, img_selected, img_cancel;

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

    public static boolean IS_UPDATING_COMMENT = false;
    private List<String> mediaPaths = new ArrayList<>();
    private RelativeLayout rl_media;
    private MultipartBody.Part media;

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
        setContentView(R.layout.activity_kikii_post_detail);

        initComponents();
        getIntentData();
        setPostData();
        loadComments();

        img_send.setOnClickListener(this);
        img_post_menu.setOnClickListener(this);
        img_back.setOnClickListener(this);
        img_select_media.setOnClickListener(this);
        img_cancel.setOnClickListener(this);

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
        //tv_name.setText(post.getUser().getName());
        tv_description.setText(post.getBody());
        /*Glide
                .with(context)
                .load(post.getUser().getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(img_user);*/
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        post = (KikiiPost) bundle.getSerializable("post");
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
                        if (responsePostComments.getComments().size() > 0){
                            tv_no.setVisibility(View.GONE);
                            rv_comments.setVisibility(View.VISIBLE);
                            setData();
                        }else{
                            customLoader.hideIndicator();
                            tv_no.setVisibility(View.VISIBLE);
                            rv_comments.setVisibility(View.GONE);
                        }
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
        commentsList=responsePostComments.getComments();
        commentsAdapter.addAll(commentsList);
        rv_comments.setAdapter(commentsAdapter);
        commentsAdapter.setOnClickListeners(new CommentsAdapter.IClicks() {
            @Override
            public void onMenuClick(View view, PostComment postComment, final int position) {
                ShowPopupMenus.showCommentMenu(activity,view,postComment,position,rv_comments,commentsList,commentsAdapter,tv_no);
            }
        });
    }

    private void initComponents() {
        customLoader = new CustomLoader(this, false);
        sessionManager = new SessionManager(this);
        commentsAdapter = new CommentsAdapter(activity);
        commentsAdapter.addAll(commentsList);

        rv_comments = findViewById(R.id.rv_comments);
        layoutManager = new LinearLayoutManager(context);
        rv_comments.setLayoutManager(layoutManager);
        rv_comments.setAdapter(commentsAdapter);

        tv_name = findViewById(R.id.tv_name);
        tv_description = findViewById(R.id.tv_description);
        tv_no=findViewById(R.id.tv_no);

        img_user = findViewById(R.id.img_user);
        img_back = findViewById(R.id.img_back);
        img_send = findViewById(R.id.img_send);
        img_post_menu=findViewById(R.id.img_post_menu);
        img_select_media = findViewById(R.id.img_select_media);
        img_selected = findViewById(R.id.img_selected);
        img_cancel=findViewById(R.id.img_cancel);

        rl_media = findViewById(R.id.rl_media);

        et_comment = findViewById(R.id.et_comment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_send:
                if (IS_UPDATING_COMMENT) {
                    if (et_comment.getText().toString().isEmpty()) {
                        et_comment.setError(getResources().getString(R.string.et_error));
                    } else {
                        comment = et_comment.getText().toString();
                        addCommentParams.put(Constants.BODY, comment);
                        addCommentParams.put(Constants.POST_ID, String.valueOf(post.getId()));

                        addComment();
                    }

                } else {
                    if (et_comment.getText().toString().isEmpty()) {
                        et_comment.setError(getResources().getString(R.string.et_error));
                    } else {
                        if(mediaPaths.size()>0){
                            media = SelectImage.prepareFilePart(Constants.PROFILE_PIC,mediaPaths.get(0));
                            addCommentWithMedia();
                        }
                        else{
                            comment = et_comment.getText().toString();
                            addCommentParams.put(Constants.BODY, comment);
                            addCommentParams.put(Constants.POST_ID, String.valueOf(post.getId()));
                            addComment();
                        }
                    }
                }
                break;
            case R.id.img_post_menu:
                //ShowPopupMenus.showPostMenu(activity,img_post_menu, post);
                break;
            case R.id.img_back:
                CommonMethods.goBack(this);
                break;
            case R.id.img_select_media:
                ShowSelectImageBottomSheet.showDialogForSelectMedia(this, img_select_media, Constants.SINGLE);
                break;
            case R.id.img_cancel:
                rl_media.setVisibility(View.GONE);
                img_selected.setImageDrawable(getResources().getDrawable(R.drawable.ic_user_dummy));
                mediaPaths.clear();
                break;
        }
    }

    private void showMenu() {

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
                        tv_no.setVisibility(View.GONE);
                        rv_comments.setVisibility(View.VISIBLE);
                        View view = KikiiPostDetailActivity.this.getCurrentFocus();
                        setResult(Activity.RESULT_OK);
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
    private void addCommentWithMedia() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackAddCommentCall = api.addCommentWithImages(sessionManager.getAccessToken(), addCommentParams,media);
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
                        tv_no.setVisibility(View.GONE);
                        rv_comments.setVisibility(View.VISIBLE);
                        setResult(RESULT_OK);
                        View view = KikiiPostDetailActivity.this.getCurrentFocus();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            Log.d("hhhh", "onActivityResult: " + mediaPaths.size());
            if (mediaPaths.size() > 0){
                rl_media.setVisibility(View.VISIBLE);
                img_selected.setImageURI(Uri.parse(mediaPaths.get(0)));
            }
        }
    }

}
