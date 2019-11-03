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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by phephen on 6/8/19.
 */

public class HomeFragment extends Fragment {

    private ListView listJobs;
    AppDelfree appDelfree;
    private boolean isBackPressedToExit;
    ArrayList<WorkOrders> list = null;
    HomeAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        listJobs=(ListView) view.findViewById(R.id.list);
        list = new ArrayList<WorkOrders>();
        adapter = new HomeAdapter(getContext(), R.layout.custom_item_home_adapter, list);
        listJobs.setAdapter(adapter);

        getDataWO("", list, adapter);

        return view;
    }

    public void getDataWO (String data, final ArrayList<WorkOrders> list, final HomeAdapter adapter){
        getData(data, list, adapter);
    }

    private void getData (String data, final ArrayList<WorkOrders> list, final HomeAdapter adapter){
        AsyncHttpTask woHttp = new AsyncHttpTask(data);
        woHttp.execute(appDelfree.HOST + appDelfree.WO, "GET");
        woHttp.setHttpResponseListener(new OnHttpResponseListener() {
            @Override
            public void OnHttpResponse(String response) {
//                Log.i("batavree ", "ini response " + response);
                try {
                    JSONObject resOBJ = new JSONObject(response);
                    if (resOBJ.getBoolean("r")){
                        JSONArray woArray = resOBJ.getJSONArray("d");
//                        Log.i("batavree", "workorder " + woArray.toString());
                        for (int i = 0; i < woArray.length(); i++) {
                            JSONObject WO = woArray.getJSONObject(i);
                            WorkOrders woOrders = new WorkOrders();
                            woOrders.setWODetails(WO.getJSONArray("WODetails"));
                            woOrders.setWODate(WO.getString("WODate"));
                            woOrders.setDriver(WO.getJSONObject("driver"));
                            woOrders.setRefNo(WO.getString("refNo"));
                            woOrders.setShipmentNum(WO.getString("shipmentNum"));
                            list.add(woOrders);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
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
