package com.jobesk.kikkiapp.Activities.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jobesk.kikkiapp.Adapters.IdentityAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class SexualIdentityActivity extends AppCompatActivity {

    private static final String TAG = "SexualIdentityActivity";
    private Context context= SexualIdentityActivity.this;
    
    private RecyclerView rv_sexual_identities;
    private IdentityAdapter identityAdapter;
    private List<String> sexualIdentitiesList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexual_identity);
        
        initComponents();

        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        sexualIdentitiesList.add("identity");
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,PronounsActivity.class));
            }
        });
    }

    private void initComponents() {
        rv_sexual_identities =findViewById(R.id.rv_sexual_identities);
        rv_sexual_identities.setLayoutManager(new LinearLayoutManager(context));
        rv_sexual_identities.setAdapter(new IdentityAdapter(sexualIdentitiesList,context));
    }
}
