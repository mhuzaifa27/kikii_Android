package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.kikkiapp.Adapters.AttendingsAdapter;
import com.example.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends AppCompatActivity {


    private static final String TAG = "ChattingActivity";
    private Context context=EventDetailActivity.this;

    private RecyclerView rv_attending;
    private AttendingsAdapter attendingsAdapter;
    private List<String> attendingUsersList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        initComponents();
        loadAttendingUsers();
    }

    private void loadAttendingUsers() {
        attendingUsersList.add("user");
        attendingUsersList.add("user");
        attendingUsersList.add("user");
        attendingUsersList.add("user");
        attendingUsersList.add("user");
        attendingUsersList.add("user");
        attendingUsersList.add("user");
        attendingUsersList.add("user");
    }

    private void initComponents() {
        rv_attending=findViewById(R.id.rv_attending);
        rv_attending.setLayoutManager(new GridLayoutManager(this,6));
        rv_attending.setAdapter(new AttendingsAdapter(attendingUsersList,this));
    }
}
