package com.delfree.delfree_android;

import android.content.Context;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static com.delfree.delfree_android.CustomArrayAdapter.ShowFragment;


/**
 * Created by phephen on 6/8/19.
 */

public class HomeFragment extends Fragment {

    private ListView listJobs;
    String[] items = new String[] {"WO No. 124/7A/VI/2019", "WO No. 124/7A/VI/2019", "WO No. 124/7A/VI/2019"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listJobs=(ListView) view.findViewById(R.id.list);

        CustomArrayAdapter arrayAdapter = new CustomArrayAdapter(getActivity(), R.layout.custom_item, items);
        listJobs.setAdapter(arrayAdapter);

        listJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailJobFragment detailJobFragment = new DetailJobFragment();
                ShowFragment(R.id.fl_container, detailJobFragment, getFragmentManager());
                Toast.makeText(getContext(), "ini tests", Toast.LENGTH_LONG).show();
            }
        });

//        listJobs.setOnItemClickListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                DetailJobFragment detailJobFragment = new DetailJobFragment();
//                ShowFragment(R.id.fl_container, detailJobFragment, getFragmentManager());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Log.i("ini error", "ini error");
//            }
//        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
