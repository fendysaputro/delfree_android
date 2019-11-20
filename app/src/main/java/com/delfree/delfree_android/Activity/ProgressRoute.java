package com.delfree.delfree_android.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.delfree.delfree_android.Adapter.ProgressRouteAdapter;
import com.delfree.delfree_android.Adapter.WorkOrderDetailAdapter;
import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Model.WorkOrderDetails;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProgressRoute extends Activity {

    AppDelfree appDelfree;
    private ListView progressList;
    ArrayList<WorkOrderDetails> list = null;
//    String[] itemProgress = new String[] {"Slipi - Grogol", "Kuningan - Tomang"};
    ProgressRouteAdapter adapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_route_activity);

        appDelfree = (AppDelfree) getApplication();
        progressList=(ListView) findViewById(R.id.list);
        list = new ArrayList<WorkOrderDetails>();

        JSONArray detailWO = getWODetails();
        for (int i = 0; i < detailWO.length(); i++) {
            try {
                JSONObject WoObj = detailWO.getJSONObject(i);
                WorkOrderDetails wod = new WorkOrderDetails();
                wod.setRoutes(WoObj.getJSONArray("routes"));
                wod.setWONum(WoObj.getString("WONum"));
                list.add(wod);
                appDelfree.setWorkOrderDetails(wod);
            } catch (JSONException ex){
                ex.printStackTrace();
            }
        }

        WorkOrderDetailAdapter workOrderDetailAdapter = new WorkOrderDetailAdapter(this, R.layout.custom_item_detailjob_adapter, list);
        progressList.setAdapter(workOrderDetailAdapter);

        adapter = new ProgressRouteAdapter(this.getApplication(), R.layout.custom_item_progress_adapter, list);
        progressList.setAdapter(adapter);

    }

    public JSONArray getWODetails (){

        return appDelfree.getWorkOrders().getWODetails();
    }
}
