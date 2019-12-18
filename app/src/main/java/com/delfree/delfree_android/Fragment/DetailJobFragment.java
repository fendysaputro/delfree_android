package com.delfree.delfree_android.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.Adapter.WorkOrderDetailAdapter;
import com.delfree.delfree_android.Adapter.HomeAdapter;
import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Model.WorkOrderDetails;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.delfree.delfree_android.MainActivity.ShowFragment;


/**
 * Created by phephen on 6/8/19.
 */

public class DetailJobFragment extends Fragment {

    private ListView listJobsById;
    TextView WONumber;
    Button startLoading;
    AppDelfree appDelfree;
    private Context context;
    Intent mServiceIntent;
    ArrayList<WorkOrderDetails> listByWo = null;
    String driverId, vehicleId, woId;
    double latitude = 0;
    double longitude = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_job, container, false);

        appDelfree = (AppDelfree) getActivity().getApplicationContext();

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

        WorkOrders selectedWorkOrder = appDelfree.getWorkOrders().get(appDelfree.getSelectedWo());

        WONumber = (TextView) view.findViewById(R.id.detail_job);
        WONumber.setText(selectedWorkOrder.getWONum());

        startLoading = (Button) view.findViewById(R.id.startLoadingBtn);
        startLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        listJobsById = (ListView) view.findViewById(R.id.list_jobs);
        listByWo = new ArrayList<WorkOrderDetails>();

        try {
            woId = selectedWorkOrder.getId();
            driverId = appDelfree.getDriver().getId();
            vehicleId = selectedWorkOrder.getVehicle().getString("_id");
            latitude = appDelfree.getLatitude();
            longitude = appDelfree.getLongitude();
        } catch (JSONException jex){
            Log.e("batavree", "error " + jex.getMessage());
        }

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

        return appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).getWODetails();
    }

    public void dialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Menuju Pick up point");
        alertDialog.setMessage("Silahkan menuju pick up point");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        AsyncHttpTask loadTask = new AsyncHttpTask("woid=" + woId +
//                                "&driverid=" + driverId +
//                                "&vehicleid=" + vehicleId +
//                                "&lang=" + latitude +
//                                "&long=" + longitude, getContext());
//                        loadTask.execute(appDelfree.HOST + appDelfree.LOADING_PATH, "POST");
//                        loadTask.setHttpResponseListener(new OnHttpResponseListener() {
//                            @Override
//                            public void OnHttpResponse(String response) {
//                                try {
//                                    JSONObject resWo = new JSONObject(response);
//                                    if (resWo.getBoolean("r")){
//                                        Toast.makeText(getActivity(), resWo.getString("m"), Toast.LENGTH_LONG).show();
//                                        Log.i("batavree", "detailJobFragment " + resWo.getJSONObject("d").toString());
//                                        appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).setStatus(resWo.getJSONObject("d").getString("status"));
//                                        Log.i("batavree", "status di detailjob " + appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).getStatus());
//                                    }
//                                } catch (JSONException jss){
//                                    Log.e("batavree", jss.getMessage());
//                                }
//                            }
//                        });
                        StartToPickUpFragment startToPickUpFragment = new StartToPickUpFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fl_container, startToPickUpFragment);
                        fragmentTransaction.commit();
                    }
                });
        alertDialog.setNegativeButton("TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alertDialog.show();

        return;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.allowBackPressed = true;
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}
