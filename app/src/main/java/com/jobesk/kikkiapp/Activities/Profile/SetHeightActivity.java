package com.jobesk.kikkiapp.Activities.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jobesk.kikkiapp.R;
import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.ArrayList;
import java.util.List;

public class SetHeightActivity extends AppCompatActivity {

    private static final String TAG = "SetHeightActivity";
    private Context context=SetHeightActivity.this;

    private ScrollChoice scroll_feet,scroll_inches;
    List<String> feetList = new ArrayList<>();
    List<String> inchesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_height);

        initComponents();

        scroll_feet.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                Log.d(TAG,name);
            }
        });
        scroll_inches.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                Log.d(TAG,name);
            }
        });

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,LookingForActivity.class));
            }
        });
    }

    private void initComponents() {

        feetList.add("4'");
        feetList.add("5'");
        feetList.add("6'");
        feetList.add("7'");
        feetList.add("8'");

        inchesList.add("0''");
        inchesList.add("1''");
        inchesList.add("2''");
        inchesList.add("3''");
        inchesList.add("4''");
        inchesList.add("5''");
        inchesList.add("6''");
        inchesList.add("7''");
        inchesList.add("8''");
        inchesList.add("9''");
        inchesList.add("10''");
        inchesList.add("11''");

        scroll_feet=findViewById(R.id.scroll_feet);
        scroll_inches=findViewById(R.id.scroll_inches);

        scroll_feet.addItems(feetList,1);
        scroll_inches.addItems(inchesList,1);
    }
}
