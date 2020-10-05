package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kikkiapp.Adapters.EventAttendantsDetailAdapter;
import com.example.kikkiapp.Callbacks.CallbackGetEvents;
import com.example.kikkiapp.Callbacks.CallbackStatus;
import com.example.kikkiapp.Model.Attendant;
import com.example.kikkiapp.Model.Event;
import com.example.kikkiapp.Netwrok.API;
import com.example.kikkiapp.Netwrok.Constant;
import com.example.kikkiapp.Netwrok.RestAdapter;
import com.example.kikkiapp.R;
import com.example.kikkiapp.Utils.CustomLoader;
import com.example.kikkiapp.Utils.SessionManager;
import com.example.kikkiapp.Utils.ShowDialogues;
import com.joooonho.SelectableRoundedImageView;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "EventDetailActivity";
    private Context context=EventDetailActivity.this;

    private RecyclerView rv_attending;
    private EventAttendantsDetailAdapter eventAttendantsDetailAdapter;
    private List<Attendant> attendingUsersList=new ArrayList<>();
    private Event event;
    private SelectableRoundedImageView img_event;
    private TextView tv_title,tv_description,tv_time,tv_user_name;
    private ImageView img_join,img_share;
    private CircleImageView img_user;

    private Map<String, String> eventParams = new HashMap<>();
    private CustomLoader customLoader;
    private SessionManager sessionManager;

    private Call<CallbackStatus> callbackAttendEvent;
    private CallbackStatus responseAttendEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        initComponents();
        getIntentData();
        setData();

        img_join.setOnClickListener(this);
    }
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        event = (Event) bundle.getSerializable("event");
    }
    private void setData() {
        tv_title.setText(event.getName());
        tv_description.setText(event.getDescription());
        tv_time.setText(event.getDatetime());
        tv_user_name.setText(event.getUser().getName());
        Glide
                .with(context)
                .load(event.getUser().getProfilePic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(img_user);

        Glide
                .with(context)
                .load(event.getCoverPic())
                .centerCrop()
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder(R.drawable.ic_user_dummy)
                .into(img_event);

        attendingUsersList=event.getAttendants();
        rv_attending.setAdapter(new EventAttendantsDetailAdapter(attendingUsersList,this));

    }

    private void initComponents() {
        customLoader=new CustomLoader(this,false);
        sessionManager=new SessionManager(context);

        img_event=findViewById(R.id.img_event);
        img_join=findViewById(R.id.img_join);
        img_share=findViewById(R.id.img_share);
        img_user=findViewById(R.id.img_user);

        tv_title=findViewById(R.id.tv_title);
        tv_time=findViewById(R.id.tv_time);
        tv_description=findViewById(R.id.tv_description);
        tv_user_name=findViewById(R.id.tv_user_name);

        rv_attending=findViewById(R.id.rv_attending);
        rv_attending.setLayoutManager(new GridLayoutManager(this,6));
    }
    private void attendEvent() {
        customLoader.showIndicator();
        API api = RestAdapter.createAPI(context);
        Log.d(TAG, "loadEvents: " + sessionManager.getAccessToken());
        callbackAttendEvent = api.attendEvent(sessionManager.getAccessToken(), eventParams);
        callbackAttendEvent.enqueue(new Callback<CallbackStatus>() {
            @Override
            public void onResponse(Call<CallbackStatus> call, Response<CallbackStatus> response) {
                Log.d(TAG, "onResponse: " + response);
                responseAttendEvent = response.body();
                if (responseAttendEvent != null) {
                    if (responseAttendEvent.getSuccess()) {
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseAttendEvent.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d(TAG, "onResponse: " + responseAttendEvent.getMessage());
                        customLoader.hideIndicator();
                        Toast.makeText(context, responseAttendEvent.getMessage(), Toast.LENGTH_SHORT).show();
                        eventParams.clear();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_join:
                eventParams.put(Constant.ID,event.getId().toString());
                attendEvent();
                break;
        }
    }
}
