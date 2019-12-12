package com.delfree.delfree_android.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import org.json.JSONException;
import org.json.JSONObject;


public class LoadingFragment extends Fragment {

    AppDelfree appDelfree;
    TextView status, charge, vehicleNo;
    Button btnStart;
    private Context context;
    String driverId, vehicleId, woId;
    double latitude = 0;
    double longitude = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        Drawable logo = getResources().getDrawable(R.drawable.logobatavree_new);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setLogo(logo);

        WorkOrders selectedWorkOrder = appDelfree.getWorkOrders().get(appDelfree.getSelectedWo());

        status = (TextView) view.findViewById(R.id.tvStatus);
        status.setText("Status : " + appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).getStatus());

        charge = (TextView) view.findViewById(R.id.tvCharge);
        charge.setText("Nama Barang : Kayu 3 ton");

        try {
            woId = selectedWorkOrder.getId();
            driverId = appDelfree.getDriver().getId();
            vehicleId = selectedWorkOrder.getVehicle().getString("_id");
            latitude = appDelfree.getLatitude();
            longitude = appDelfree.getLongitude();
        } catch (JSONException jex){
            Log.e("batavree", "error " + jex.getMessage());
        }

        try {
            vehicleNo = (TextView) view.findViewById(R.id.tvVehicleNo);
            vehicleNo.setText("Plat Nomor : " + appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).getVehicle().getString("police_no"));
            }catch (JSONException jsonEx){
                Log.e("batavree", "error" + jsonEx.getMessage());
            }

        btnStart = (Button) view.findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        return view;
    }

    public void dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Mulai Perjalanan");
        alertDialog.setMessage("Apakah anda yakin memulai perjalanan?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncHttpTask startTask = new AsyncHttpTask("woid=" + woId +
                                "&driverid=" + driverId +
                                "&vehicleid=" + vehicleId +
                                "&lang=" + latitude +
                                "&long=" + longitude, getContext());
                        startTask.execute(appDelfree.HOST + appDelfree.START_PATH, "POST");
                        startTask.setHttpResponseListener(new OnHttpResponseListener() {
                            @Override
                            public void OnHttpResponse(String response) {
                                try {
                                    JSONObject resWo = new JSONObject(response);
                                    if (resWo.getBoolean("r")){
                                        Toast.makeText(getActivity(), resWo.getString("m"), Toast.LENGTH_LONG).show();
//                                        resWo.getJSONObject("d");
                                        Log.i("batavree", "d " + resWo.getJSONObject("d").toString());
                                        appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).setStatus(resWo.getJSONObject("d").getString("status"));
                                    }
                                } catch (JSONException jss){
                                    Log.e("batavree", jss.getMessage());
                                }
                            }
                        });
                        context = getContext();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startService(new Intent(context, AppDataService.class));
                        context.startActivity(intent);
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

    public void onResume () {
        super.onResume();
        status.setText("Status : " + appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).getStatus());
        Log.i("batavree", "loading " + appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).getStatus());
    }
}
