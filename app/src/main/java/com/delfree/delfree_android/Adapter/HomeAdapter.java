package com.delfree.delfree_android.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Fragment.DetailJobFragment;
import com.delfree.delfree_android.Fragment.LoadingFragment;
import com.delfree.delfree_android.Fragment.StartToPickUpFragment;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.delfree.delfree_android.MainActivity.ShowFragment;

public class HomeAdapter extends ArrayAdapter {

    private ArrayList<WorkOrders> myListJobs;
    private Context context;
    Intent mServiceIntent;
    public AppDataService appDataService;
    public boolean mTracking = false;
    Activity activity;
    AppDelfree appDelfree;
    String driverId, vehicleId, woId;
    double latitude = 0;
    double longitude = 0;
    TextView textView;
//    WorkOrders selectedWorkOrder;

    public HomeAdapter(Context context, int textViewResourceId, ArrayList<WorkOrders> myListJobs) {
        super(context, textViewResourceId, myListJobs);

        this.myListJobs = myListJobs;
        this.context = context;
        appDelfree = (AppDelfree) this.context.getApplicationContext();
    }


    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_home_adapter, null);

        appDataService = new AppDataService();
        mServiceIntent = new Intent(context, appDataService.getClass());
        mServiceIntent = new Intent(context, appDelfree.getClass());

        textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(myListJobs.get(position).getWONum());

        try {
            String WoDate = myListJobs.get(position).getWODate();
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(WoDate);
            String formattedDate = new SimpleDateFormat("dd MMM yyyy").format(date);
            TextView textViewDate = (TextView) view.findViewById(R.id.tvDate);
            textViewDate.setText(formattedDate);
        } catch (ParseException e){
            e.getStackTrace();
        }


        ImageButton moreBtn = (ImageButton) view.findViewById(R.id.iconList);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appDelfree.setSelectedWo(position);
                dialog();
//                DetailJobFragment detailJobFragment = new DetailJobFragment();
//                Activity activity = (Activity) context;
//                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//                ShowFragment(R.id.fl_container, detailJobFragment,fragmentManager);
//                StartToPickUpFragment startToPickUpFragment = new StartToPickUpFragment();
//                Activity activity = (Activity) context;
//                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//                ShowFragment(R.id.fl_container, startToPickUpFragment,fragmentManager);
//                try {
//                    woId = myListJobs.get(appDelfree.getSelectedWo()).getId();
//                    driverId = myListJobs.get(appDelfree.getSelectedWo()).getDriver().getString("_id");
//                    vehicleId = myListJobs.get(appDelfree.getSelectedWo()).getVehicle().getString("_id");
//                    latitude = appDelfree.getLatitude();
//                    longitude = appDelfree.getLongitude();
//                } catch (JSONException jex){
//                    Log.e("batavree", "error " + jex.getMessage());
//                }
//                AsyncHttpTask loadTask = new AsyncHttpTask("woid=" + woId +
//                        "&driverid=" + driverId +
//                        "&vehicleid=" + vehicleId +
//                        "&lang=" + latitude +
//                        "&long=" + longitude, getContext());
//                loadTask.execute(appDelfree.HOST + appDelfree.LOADING_PATH, "POST");
//                loadTask.setHttpResponseListener(new OnHttpResponseListener() {
//                    @Override
//                    public void OnHttpResponse(String response) {
//                        try {
//                            JSONObject resWo = new JSONObject(response);
//                            if (resWo.getBoolean("r")){
////                                Toast.makeText(getContext(), resWo.getString("m"), Toast.LENGTH_LONG).show();
//                                myListJobs.get(appDelfree.getSelectedWo()).setStatus(resWo.getJSONObject("d").getString("status"));
//                            }
//                        } catch (JSONException jss){
//                            Log.e("batavree", jss.getMessage());
//                        }
//                    }
//                });
            }
        });

        return view;
    }

    public void dialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Menuju Pick Up Point");
        alertDialog.setMessage("Apakah anda yakin menuju pick up point?");
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
                        Activity activity = (Activity) context;
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        ShowFragment(R.id.fl_container, startToPickUpFragment,fragmentManager);
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
}
