package com.jobesk.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jobesk.kikkiapp.Adapters.ChattingAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChattingActivity";
    private Context context=ChattingActivity.this;

    private RecyclerView rv_chatting;
    private ChattingAdapter chattingAdapter;
    private List<String> chatList=new ArrayList<>();

    private ImageView img_video_call,img_voice_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        initComponents();
        loadChat();

        img_video_call.setOnClickListener(this);
        img_voice_call.setOnClickListener(this);
    }

    private void loadChat() {
        chatList.add("0");
        chatList.add("0");
        chatList.add("1");
        chatList.add("0");
        chatList.add("1");
        chatList.add("0");
        chatList.add("1");
        chatList.add("0");
        chatList.add("1");

    }

    private void initComponents() {
        img_video_call=findViewById(R.id.img_video_call);
        img_voice_call=findViewById(R.id.img_voice_call);

        rv_chatting=findViewById(R.id.rv_chatting);
        rv_chatting.setLayoutManager(new LinearLayoutManager(context));
        rv_chatting.setAdapter(new ChattingAdapter(chatList,context));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_video_call:
                startActivity(new Intent(context,CallingActivity.class));
                break;
            case R.id.img_voice_call:
                startActivity(new Intent(context,CallingActivity.class));
                break;
        }
    }
}
