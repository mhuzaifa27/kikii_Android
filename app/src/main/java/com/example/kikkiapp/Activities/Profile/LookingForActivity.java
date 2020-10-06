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

public class LookingForActivity extends AppCompatActivity {

    private static final String TAG = "LookingForActivity";
    private Context context= LookingForActivity.this;

    private RecyclerView rv_gender_identities;
    private IdentityAdapter identityAdapter;
    private List<String> genderIdentitiesList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_for);

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
                startActivity(new Intent(context,DoYouDrinkActivity.class));
            }
        });
    }

    private void initComponents() {
        rv_gender_identities=findViewById(R.id.rv_gender_identities);
        rv_gender_identities.setLayoutManager(new LinearLayoutManager(context));
        rv_gender_identities.setAdapter(new IdentityAdapter(genderIdentitiesList,context,""));
    }
}
