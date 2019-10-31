package com.delfree.delfree_android.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.Adapter.HomeAdapter;
import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.R;

import java.util.ArrayList;


/**
 * Created by phephen on 6/8/19.
 */

public class HomeFragment extends Fragment {

    private ListView listJobs;
    String[] items = new String[] {"WO No. 124/7A/VI/2019", "WO No. 124/7A/VI/2019", "WO No. 124/7A/VI/2019"};
    TextView textView;
    String name;
    AppDelfree appDelfree;
    private boolean isBackPressedToExit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        getData("");
        listJobs=(ListView) view.findViewById(R.id.list);

        HomeAdapter arrayAdapter = new HomeAdapter(getActivity(), R.layout.custom_item_home_adapter, items);
        listJobs.setAdapter(arrayAdapter);

        return view;
    }

    private void getData (String data){
        AsyncHttpTask woHttp = new AsyncHttpTask(data);
        woHttp.execute(appDelfree.HOST + appDelfree.WO, "GET");
        woHttp.setHttpResponseListener(new OnHttpResponseListener() {
            @Override
            public void OnHttpResponse(String response) {
                Log.i("batavree ", "ini response " + response);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity.allowBackPressed = false;
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
