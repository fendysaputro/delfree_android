package com.delfree.delfree_android.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.Adapter.WorkOrderDetailAdapter;
import com.delfree.delfree_android.Adapter.HomeAdapter;
import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Model.WorkOrderDetails;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by phephen on 6/8/19.
 */

public class DetailJobFragment extends Fragment {

    private ListView listJobsById;
//    String[] dropPoint = new String[] {"Drop point 1: Bandung Timur", "Drop point 2: Bandung Selatan", "Drop point 3: Bandung Barat"};
    TextView WONumber;
    AppDelfree appDelfree;
    private Context context;
    Intent mServiceIntent;
    ArrayList<WorkOrderDetails> listByWo = null;
//    DetailJobAdapter detailJobAdapter = null;

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
        WONumber.setText(appDelfree.getWorkOrders().getWONum());

//        Log.i("batavree", "wo_detail " + appDelfree.getWorkOrders().getWODetails().toString());

        listJobsById = (ListView) view.findViewById(R.id.list_jobs);
        listByWo = new ArrayList<WorkOrderDetails>();

//        DetailJobAdapter arrayAdapter = new DetailJobAdapter(getActivity(), R.layout.custom_item_home_adapter, dropPoint);
//        listJobsById.setAdapter(arrayAdapter);

        JSONArray detailWO = getWODetails();
        for (int i = 0; i < detailWO.length(); i++) {
            try {
                JSONObject WoObj = detailWO.getJSONObject(i);
                WorkOrderDetails wod = new WorkOrderDetails();
                wod.setRoutes(WoObj.getJSONArray("routes"));
                wod.setWONum(WoObj.getString("WONum"));
                listByWo.add(wod);
                appDelfree.setWorkOrderDetails(wod);
            } catch (JSONException ex){
                ex.printStackTrace();
            }
        }

        WorkOrderDetailAdapter workOrderDetailAdapter = new WorkOrderDetailAdapter(getContext(), R.layout.custom_item_detailjob_adapter, listByWo);
        listJobsById.setAdapter(workOrderDetailAdapter);

        return view;
    }

    public JSONArray getWODetails (){

        return appDelfree.getWorkOrders().getWODetails();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}
