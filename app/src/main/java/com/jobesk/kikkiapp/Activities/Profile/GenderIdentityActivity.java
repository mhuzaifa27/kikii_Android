package com.jobesk.kikkiapp.Activities.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jobesk.kikkiapp.Adapters.IdentityAdapter;
import com.jobesk.kikkiapp.R;

import java.util.ArrayList;
import java.util.List;

public class GenderIdentityActivity extends AppCompatActivity {

    private static final String TAG = "GenderIdentity";
    private Context context= GenderIdentityActivity.this;

    private RecyclerView rv_gender_identities;
    private IdentityAdapter identityAdapter;
    private List<String> genderIdentitiesList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender_identity);

        initComponents();

        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");
        genderIdentitiesList.add("identity");

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,SexualIdentityActivity.class));
            }
        });
    }

    private void initComponents() {
        rv_gender_identities=findViewById(R.id.rv_gender_identities);
        rv_gender_identities.setLayoutManager(new LinearLayoutManager(context));
        rv_gender_identities.setAdapter(new IdentityAdapter(genderIdentitiesList,context));
    }
}
