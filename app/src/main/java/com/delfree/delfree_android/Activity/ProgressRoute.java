package com.delfree.delfree_android.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Fragment.FinishJobFragment;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Model.WorkOrderDetails;
import com.delfree.delfree_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.delfree.delfree_android.MainActivity.ShowFragment;
import static java.security.AccessController.getContext;

public class ProgressRoute extends AppCompatActivity {

    AppDelfree appDelfree;
    private ListView progressList;
    ArrayList<WorkOrderDetails> list = null;
//    String[] itemProgress = new String[] {"Slipi - Grogol", "Kuningan - Tomang"};
    Button btnFinish;
    TextView status, addressFrom, addressTo;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_route_activity);

        appDelfree = (AppDelfree) getApplication();
        context = appDelfree.getApplicationContext();
//        progressList=(ListView) findViewById(R.id.list);
//        list = new ArrayList<WorkOrderDetails>();
//
        JSONArray detailWO = getWODetails();
        for (int i = 0; i < detailWO.length(); i++) {
            try {
                JSONObject WoObj = detailWO.getJSONObject(i);
                WorkOrderDetails wod = new WorkOrderDetails();
                wod.setRoutes(WoObj.getJSONArray("routes"));
                wod.setWONum(WoObj.getString("WONum"));
//                list.add(wod);
                appDelfree.setWorkOrderDetails(wod);

                status = (TextView) findViewById(R.id.tvStatus);
                status.setText("Status : On Progress");

                addressFrom = (TextView) findViewById(R.id.tvRouteSrc);
                addressFrom.setText("Alamat dari : " + appDelfree.getWorkOrderDetails().getRoutes().getJSONObject(i).getJSONObject("src").getString("addr"));

                addressTo = (TextView) findViewById(R.id.tvRouteDest);
                addressTo.setText("Alamat ke : " + appDelfree.getWorkOrderDetails().getRoutes().getJSONObject(i).getJSONObject("dest").getString("addr"));

            } catch (JSONException ex){
                ex.printStackTrace();
            }
        }
//
//        WorkOrderDetailAdapter workOrderDetailAdapter = new WorkOrderDetailAdapter(this, R.layout.custom_item_detailjob_adapter, list);
//        progressList.setAdapter(workOrderDetailAdapter);
//
//        adapter = new ProgressRouteAdapter(this.getApplication(), R.layout.custom_item_progress_adapter, list);
//        progressList.setAdapter(adapter);


        btnFinish = (Button) findViewById(R.id.buttonFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

    }

    public JSONArray getWODetails (){

        return appDelfree.getWorkOrders().getWODetails();
    }

    public void dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Selesai Perjalanan");
        alertDialog.setMessage("Apakah anda yakin mengakhiri perjalanan?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FinishJobFragment finishJobFragment = new FinishJobFragment();
//                        Activity activity = (Activity) context;
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        ShowFragment(R.id.fl_container, finishJobFragment,fragmentManager);

//                        stopService(new Intent(getApplication(), AppDataService.class));
                    }
                });
        alertDialog.setNegativeButton("TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alertDialog.show();

        return;
    }
}
