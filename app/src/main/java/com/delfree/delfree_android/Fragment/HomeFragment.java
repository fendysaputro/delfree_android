package com.delfree.delfree_android.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.delfree.delfree_android.Adapter.HomeAdapter;
import com.delfree.delfree_android.ForgotPasswordPage;
import com.delfree.delfree_android.Fragment.DetailJobFragment;
import com.delfree.delfree_android.GpsTracking;
import com.delfree.delfree_android.LoginPage;
import com.delfree.delfree_android.R;

import static com.delfree.delfree_android.MainActivity.ShowFragment;


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

        HomeAdapter arrayAdapter = new HomeAdapter(getActivity(), R.layout.custom_item, items);
        listJobs.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
