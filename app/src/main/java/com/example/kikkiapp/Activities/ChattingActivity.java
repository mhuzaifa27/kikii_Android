package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kikkiapp.Adapters.ChattingAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetConversationMessages;
import com.example.kikkiapp.Callbacks.CallbackGetMatch;
import com.example.kikkiapp.Callbacks.CallbackSendMessage;
import com.example.kikkiapp.Model.Message;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChattingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChattingActivity";
    private Context context = ChattingActivity.this;
    private Activity activity = ChattingActivity.this;

    private RecyclerView rv_chatting;
    private ChattingAdapter chattingAdapter;
    private List<Message> chatList = new ArrayList<>();

    private ImageView img_video_call, img_voice_call;
    private EditText et_message;
    private ImageView img_voice, img_send;

    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackGetConversationMessages> callbackGetConversationMessagesCall;
    private CallbackGetConversationMessages responseGetConversationMessages;

    private Call<CallbackSendMessage> callbackSendMessageCall;
    private CallbackSendMessage responseSendMessage;

    private Map<String, String> sendMessageParam = new HashMap<>();

    private String conversationId, receiverId;
    private int count = 0;
    private TextView tv_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        getIntentData();
        initComponents();

        if (conversationId != null)
            loadChat();
        else
            tv_no.setVisibility(View.VISIBLE);

        img_video_call.setOnClickListener(this);
        img_voice_call.setOnClickListener(this);
        img_send.setOnClickListener(this);


        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    img_voice.setVisibility(View.GONE);
                    img_send.setVisibility(View.VISIBLE);
                } else {
                    img_voice.setVisibility(View.VISIBLE);
                    img_send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getIntentData() {
        receiverId = getIntent().getStringExtra(Constant.ID);
    }

    private void loadChat() {
        chatList.clear();
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackGetConversationMessagesCall = api.getConversationMessages(sessionManager.getAccessToken(), conversationId);
        callbackGetConversationMessagesCall.enqueue(new Callback<CallbackGetConversationMessages>() {
            @Override
            public void onResponse(Call<CallbackGetConversationMessages> call, Response<CallbackGetConversationMessages> response) {
                Log.d(TAG, "onResponse: " + response);
                responseGetConversationMessages = response.body();
                if (responseGetConversationMessages != null) {
                    if (responseGetConversationMessages.getSuccess()) {
                        if (responseGetConversationMessages.getMessages().size() > 0) {
                            rv_chatting.setVisibility(View.VISIBLE);
                            tv_no.setVisibility(View.GONE);
                            setData();
                        } else {
                            tv_no.setVisibility(View.VISIBLE);
                            rv_chatting.setVisibility(View.GONE);
                        }
                        /*else
                            currentPage = -1;*/
                    } else {
                        Log.d(TAG, "onResponse: " + responseGetConversationMessages.getMessage());
                        Toast.makeText(context, responseGetConversationMessages.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackGetConversationMessages> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }

    private void setData() {
        customLoader.hideIndicator();
        rv_chatting.setLayoutManager(new LinearLayoutManager(context));
        rv_chatting.setAdapter(new ChattingAdapter(chatList, context));
    }

    private void initComponents() {
        customLoader = new CustomLoader(activity, false);
        sessionManager = new SessionManager(context);

        img_video_call = findViewById(R.id.img_video_call);
        img_voice_call = findViewById(R.id.img_voice_call);
        img_send = findViewById(R.id.img_send);
        img_voice = findViewById(R.id.img_voice);

        et_message = findViewById(R.id.et_message);

        rv_chatting = findViewById(R.id.rv_chatting);
        chattingAdapter=new ChattingAdapter(chatList,context);

        tv_no=findViewById(R.id.tv_no);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_video_call:
                startActivity(new Intent(context, CallingActivity.class));
                break;
            case R.id.img_voice_call:
                startActivity(new Intent(context, CallingActivity.class));
                break;
            case R.id.img_send:
                if (et_message.getText().toString().isEmpty()) {
                    et_message.setError(getResources().getString(R.string.et_error));
                } else {
                    sendMessageParam.put(Constant.BODY, et_message.getText().toString());
                    sendMessageParam.put(Constant.RECEIVER_ID, receiverId);
                    sendMessage();
                }
                break;
        }
    }

    private void sendMessage() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadCommunityPosts: " + sessionManager.getAccessToken());
        callbackSendMessageCall = api.sendMessage(sessionManager.getAccessToken(), sendMessageParam);
        callbackSendMessageCall.enqueue(new Callback<CallbackSendMessage>() {
            @Override
            public void onResponse(Call<CallbackSendMessage> call, Response<CallbackSendMessage> response) {
                Log.d(TAG, "onResponse: " + response);
                responseSendMessage = response.body();
                if (responseSendMessage != null) {
                    if (responseSendMessage.getSuccess()) {
                        Message message = responseSendMessage.getData();
                        chatList.add(message);
                        chattingAdapter.notifyDataSetChanged();
                        conversationId = message.getConversationId().toString();
                        if (count == 0){
                            loadChat();
                            count++;
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + responseGetConversationMessages.getMessage());
                        Toast.makeText(context, responseGetConversationMessages.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    customLoader.hideIndicator();
                    ShowDialogues.SHOW_SERVER_ERROR_DIALOG(context);
                }
            }

            @Override
            public void onFailure(Call<CallbackSendMessage> call, Throwable t) {
                if (!call.isCanceled()) {
                    Log.d(TAG, "onResponse: " + t.getMessage());
                    customLoader.hideIndicator();
                }
            }
        });
    }
}
