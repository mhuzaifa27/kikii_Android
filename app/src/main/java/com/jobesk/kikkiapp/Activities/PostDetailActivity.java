package com.jobesk.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.jobesk.kikkiapp.Adapters.CommentsAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {

    private static final String TAG = "ChattingActivity";
    private Context context=PostDetailActivity.this;

    private RecyclerView rv_comments;
    private CommentsAdapter commentsAdapter;
    private List<String> commentsList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initComponents();
        loadChat();

    }

    private void loadChat() {
        commentsList.add("0");
        commentsList.add("0");
        commentsList.add("1");
        commentsList.add("0");
        commentsList.add("1");
        commentsList.add("0");
        commentsList.add("1");
        commentsList.add("0");
    }

    private void initComponents() {

        rv_comments =findViewById(R.id.rv_comments);
        rv_comments.setLayoutManager(new LinearLayoutManager(context));
        rv_comments.setAdapter(new CommentsAdapter(commentsList,context));
    }
}
