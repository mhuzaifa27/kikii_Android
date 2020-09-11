package com.jobesk.kikkiapp.Fragments.Main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jobesk.kikkiapp.Adapters.SwipeFlingMeetAdapter;
import com.jobesk.kikkiapp.R;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MeetFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MeetFragment";
    private Context context=getContext();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<String> list=new ArrayList<>();
    private SwipeFlingMeetAdapter adapter;
    private ImageView img_open_details,img_close_details;
    private LinearLayout ll_normal_view;
    private RelativeLayout rl_detail_view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meet, container, false);
        initComponents(view);

        //rl_detail_view.setVisibility(View.GONE);

            /*// Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
            // Populate the data into the template view using the data object
            tvName.setText(user.name);
            tvHome.setText(user.hometown);*/

        img_close_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                rl_detail_view.setVisibility(View.GONE);
                ll_normal_view.setVisibility(View.VISIBLE);
            }
        });

        img_open_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
                rl_detail_view.setVisibility(View.VISIBLE);
                ll_normal_view.setVisibility(View.GONE);
            }
        });


        //add the view via xml or programmatically
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) view.findViewById(R.id.swipe_frame);

        list.add("php");
        list.add("c");
        list.add("python");
        list.add("java");
        adapter=new SwipeFlingMeetAdapter(getContext(),list);
        flingContainer.setAdapter(adapter);

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                list.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                //Toast.makeText(getContext(), "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //Toast.makeText(getContext(), "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
//                // Ask for more data here
//                list.add("XML ".concat(String.valueOf(i)));
//                adapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float v) {

            }
        });

        flingContainer.setVerticalScrollBarEnabled(true);
        flingContainer.computeScroll();
        flingContainer.setNestedScrollingEnabled(true);


        /*// Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                //makeToast(MyActivity.this, "Clicked!");
            }
        });*/
        return view;
    }

    private void initComponents(View view) {
        img_open_details=view.findViewById(R.id.img_open_details);
        img_close_details=view.findViewById(R.id.img_close_details);

        ll_normal_view=view.findViewById(R.id.ll_normal_view);
        rl_detail_view=view.findViewById(R.id.rl_detail_view);
    }

    @Override
    public void onRefresh() {

    }
}
