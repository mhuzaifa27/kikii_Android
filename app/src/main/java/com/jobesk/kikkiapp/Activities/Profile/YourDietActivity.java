package com.jobesk.kikkiapp.Activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jobesk.kikkiapp.Adapters.TabsAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class YourDietActivity extends AppCompatActivity {

    private static final String TAG = "YourDietActivity";
    private Context context= YourDietActivity.this;
    
    private RecyclerView rv_statuses;
    private TabsAdapter tabsAdapter;
    private List<String> statusesList=new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_diet);
        
        initComponents();

        statusesList.add("No");
        statusesList.add("Occasionally");
        statusesList.add("Socially");
        statusesList.add("Prefer not to say");
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                if (position == 0)
                {
                    return 3;
                }
                else if(position == 1)
                {
                    return 2;
                }
                else if(position == 2)
                {
                    return 3;
                }
                else if(position == 3)
                {
                    return 1;
                }
                else{
                    return 3;
                }
            }
        });
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,YourSignActivity.class));
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
