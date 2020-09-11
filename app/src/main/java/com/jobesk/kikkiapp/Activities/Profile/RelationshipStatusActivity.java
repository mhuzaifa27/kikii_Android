package com.jobesk.kikkiapp.Activities.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jobesk.kikkiapp.Adapters.TabsAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class RelationshipStatusActivity extends AppCompatActivity {

    private static final String TAG = "RelationshipStatusActiv";
    private Context context=RelationshipStatusActivity.this;
    
    private RecyclerView rv_statuses;
    private TabsAdapter tabsAdapter;
    private List<String> statusesList=new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relationship_status);
        
        initComponents();

        statusesList.add("Lorem Ipsum");
        statusesList.add("Lorem");
        statusesList.add("Single");
        statusesList.add("Not interested");
        statusesList.add("Married");
        statusesList.add("Yes");

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,SetHeightActivity.class));
            }
        });

    }

    private void initComponents() {
        rv_statuses=findViewById(R.id.rv_statuses);
        gridLayoutManager = new GridLayoutManager(context, 3);
        rv_statuses.setLayoutManager(gridLayoutManager);
        rv_statuses.setAdapter(new TabsAdapter(statusesList,context));

    }

}
