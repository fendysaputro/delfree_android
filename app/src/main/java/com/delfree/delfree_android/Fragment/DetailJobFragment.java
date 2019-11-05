package com.delfree.delfree_android.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.delfree.delfree_android.Adapter.DetailJobAdapter;
import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.R;


/**
 * Created by phephen on 6/8/19.
 */

public class DetailJobFragment extends Fragment {

    private ListView listJobsById;
    String[] dropPoint = new String[] {"Drop point 1: Bandung Timur", "Drop point 2: Bandung Selatan", "Drop point 3: Bandung Barat"};
    TextView WONumber;
    AppDelfree appDelfree;
    private Context context;
    Intent mServiceIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_job, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Back");
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        WONumber = (TextView) view.findViewById(R.id.detail_job);
        WONumber.setText(appDelfree.getWo_Number());
        Log.i("batavree", "wo_detail " + appDelfree.getWo_Number());

        listJobsById = (ListView) view.findViewById(R.id.list_jobs);

        DetailJobAdapter arrayAdapter = new DetailJobAdapter(getActivity(), R.layout.custom_item_home_adapter, dropPoint);
        listJobsById.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}
