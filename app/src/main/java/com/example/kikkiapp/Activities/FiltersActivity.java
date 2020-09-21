package com.example.kikkiapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kikkiapp.Adapters.PremiumFiltersAdapter;
import com.example.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class FiltersActivity extends AppCompatActivity {

    private static final String TAG = "FiltersActivity";
    private Context context= FiltersActivity.this;

    private RecyclerView rv_premium_filters;
    private PremiumFiltersAdapter premiumFiltersAdapter;
    private List<String> premiumFilterList =new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        initComponents();

        premiumFilterList.add("No");
        premiumFilterList.add("Occasionally");
        premiumFilterList.add("Socially");
        premiumFilterList.add("Prefer not to say");

        findViewById(R.id.tv_any_age).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, FriendsActivity.class));
            }
        });

    }

    private void initComponents() {
        rv_premium_filters =findViewById(R.id.rv_premium_filters);
        gridLayoutManager = new GridLayoutManager(context, 3);
        rv_premium_filters.setLayoutManager(gridLayoutManager);
        rv_premium_filters.setAdapter(new PremiumFiltersAdapter(premiumFilterList,context));
    }
}
