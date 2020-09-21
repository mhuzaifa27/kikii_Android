package com.example.kikkiapp.Activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kikkiapp.Adapters.IdentityAdapter;
import com.example.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class PronounsActivity extends AppCompatActivity {

    private static final String TAG = "PronounsActivity";
    private Context context= PronounsActivity.this;
    
    private RecyclerView rv_pronouns;
    private IdentityAdapter identityAdapter;
    private List<String> pronounsList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouns);
        
        initComponents();

        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        pronounsList.add("pronouns");
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,AddBioActivity.class));
            }
        });
    }

    private void initComponents() {
        rv_pronouns =findViewById(R.id.rv_pronouns);
        rv_pronouns.setLayoutManager(new LinearLayoutManager(context));
        rv_pronouns.setAdapter(new IdentityAdapter(pronounsList,context));
    }
}
