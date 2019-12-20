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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

/**
 * created by phephen 2019
 */
public class UnloadingFragment extends Fragment {
    AppDelfree appDelfree;
    TextView statusUnloading, charge, vehicleNo;
    Button btnFinish;
    private Context context;
    String driverId, vehicleId, woId;
    double latitude = 0;
    double longitude = 0;
    WorkOrders selectedWorkOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unloading, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        Drawable logo = getResources().getDrawable(R.drawable.logobatavree_new);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setLogo(logo);

        selectedWorkOrder = appDelfree.getSelectedWo();

        statusUnloading = (TextView) view.findViewById(R.id.tvStatus);
        statusUnloading.setText("Status : " + selectedWorkOrder.getStatus());

        charge = (TextView) view.findViewById(R.id.tvCharge);
        charge.setText("Nama Barang : Kayu 3 ton");

        try {
            vehicleNo = (TextView) view.findViewById(R.id.tvVehicleNo);
            vehicleNo.setText("Plat Nomor : " + selectedWorkOrder.getVehicle().getString("police_no"));
        }catch (JSONException jsonEx){
            Log.e("batavree", "error" + jsonEx.getMessage());
        }

        try {
            woId = selectedWorkOrder.getId();
            driverId = appDelfree.getDriver().getId();
            vehicleId = selectedWorkOrder.getVehicle().getString("_id");
            latitude = appDelfree.getLatitude();
            longitude = appDelfree.getLongitude();
        } catch (JSONException jex){
            Log.e("batavree", "error " + jex.getMessage());
        }

        btnFinish = (Button) view.findViewById(R.id.buttonFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        return view;
    }

    public void dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Selesai Perjalanan");
        alertDialog.setMessage("Apakah anda yakin mengakhiri perjalanan?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncHttpTask startTask = new AsyncHttpTask("woid=" + woId +
                                "&driverid=" + driverId +
                                "&vehicleid=" + vehicleId +
                                "&lang=" + latitude +
                                "&long=" + longitude, getContext());
                        startTask.execute(appDelfree.HOST + appDelfree.FINISH_PATH, "POST");
                        startTask.setHttpResponseListener(new OnHttpResponseListener() {
                            @Override
                            public void OnHttpResponse(String response) {
                                Log.i("batavree", "unloading fragment response " + response);
                                Toast.makeText(getActivity(), "uload fragment ", Toast.LENGTH_SHORT).show();
                                try {
                                    JSONObject resWo = new JSONObject(response);
                                    if (resWo.getBoolean("r")){
                                        Toast.makeText(getActivity(), resWo.getString("m"), Toast.LENGTH_LONG).show();
//                                        resWo.getJSONObject("d");
                                        Log.i("batavree", "unloading fragment " + resWo.getJSONObject("d").toString());
                                        appDelfree.getSelectedWo().setStatus(resWo.getJSONObject("d").getString("status"));
                                        statusUnloading.setText("Status : " + appDelfree.getSelectedWo().getStatus());
                                    }
                                } catch (JSONException jss){
                                    Log.e("batavree", jss.getMessage());
                                }
                            }
                        });

                        getActivity().stopService(new Intent(getActivity(), AppDataService.class));
                        FinishJobFragment finishJobFragment = new FinishJobFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fl_container, finishJobFragment);
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

    public void getDataStatus () {
        statusUnloading.setText("Status : " + selectedWorkOrder.getStatus() );
        Log.i("batavree", "unloading barang 3" + selectedWorkOrder.getStatus());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        getDataStatus();
    }
}
