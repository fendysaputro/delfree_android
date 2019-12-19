package com.delfree.delfree_android.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * created by phephen 2019
 */
public class StartToPickUpFragment extends Fragment {
    AppDelfree appDelfree;
    TextView status, charge, vehicleNo;
    TextView WONumber;
    Button btnStart;
    private Context context;
    String driverId, vehicleId, woId;
    double latitude = 0;
    double longitude = 0;
    WorkOrders selectedWorkOrder;
    View view;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_start_to_pick_up, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        Drawable logo = getResources().getDrawable(R.drawable.logobatavree_new);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setLogo(logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));

        selectedWorkOrder = appDelfree.getWorkOrders().get(appDelfree.getSelectedWo());

        WONumber = (TextView) view.findViewById(R.id.detail_job);
        WONumber.setText(selectedWorkOrder.getWONum());

        status = (TextView) view.findViewById(R.id.tvStatus);
        status.setText("Status : menuju pick up");

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
            vehicleNo.setText("Plat Nomor : " + selectedWorkOrder.getVehicle().getString("police_no"));
        }catch (JSONException jsonEx){
            Log.e("batavree", "error" + jsonEx.getMessage());
        }

        spinner = (Spinner) view.findViewById(R.id.spinnerJobs);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String statusLoad = parent.getItemAtPosition(position).toString();
                Log.i("batavree", "status position " + statusLoad);
//                status.setText("Status : " + statusLoad);
                btnStart.setText(statusLoad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final List<String> loadStatus = new ArrayList<>();
        loadStatus.add(" Muat Barang ");
        loadStatus.add(" Menunggu Antrian ");

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, loadStatus);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(statusAdapter);

        btnStart = (Button) view.findViewById(R.id.buttonStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnStart.getText().equals(" Muat Barang ")){
                    dialogLoading();
                } else {
                    dialogWaitLoading();
                }
            }
        });

        return view;
    }

    public void dialogLoading() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Menaikan Muatan");
        alertDialog.setMessage("Apakah anda yakin untuk menaikan muatan?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncHttpTask unloadTask = new AsyncHttpTask("woid=" + woId +
                                "&driverid=" + driverId +
                                "&vehicleid=" + vehicleId +
                                "&lang=" + latitude +
                                "&long=" + longitude, getContext());
                        unloadTask.execute(appDelfree.HOST + appDelfree.LOADING_PATH, "POST");
                        unloadTask.setHttpResponseListener(new OnHttpResponseListener() {
                            @Override
                            public void OnHttpResponse(String response) {
                                try {
                                    JSONObject resWo = new JSONObject(response);
                                    if (resWo.getBoolean("r")){
                                        Toast.makeText(getActivity(), resWo.getString("m"), Toast.LENGTH_LONG).show();
                                        selectedWorkOrder.setStatus(resWo.getJSONObject("d").getString("status"));
                                        status.setText("Status : " + selectedWorkOrder.getStatus());
                                        charge.setText("Nama Barang : Kayu 3 ton");
                                        try {
                                            vehicleNo.setText("Plat Nomor : " + selectedWorkOrder.getVehicle().getString("police_no"));
                                        }catch (JSONException jsonEx){
                                            Log.e("batavree", "error" + jsonEx.getMessage());
                                        }
                                        spinner.setVisibility(View.INVISIBLE);
                                        btnStart.setText(" Ambil Foto ");
                                        btnStart.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogTakePhoto();
                                            }
                                        });
                                    }
                                } catch (JSONException jss){
                                    Log.e("batavree", jss.getMessage());
                                }
                            }
                        });
                    }
                });
        alertDialog.setNegativeButton("TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alertDialog.show();

        return;
    }

    public void dialogWaitLoading() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Menunggu Antrian");
        alertDialog.setMessage("Silahkan tunggu antrian");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        AsyncHttpTask toUnloadTask = new AsyncHttpTask("woid=" + woId +
//                                "&driverid=" + driverId +
//                                "&vehicleid=" + vehicleId +
//                                "&lang=" + latitude +
//                                "&long=" + longitude, getContext());
//                        toUnloadTask.execute(appDelfree.HOST + appDelfree.FINISH_PATH, "POST");
//                        toUnloadTask.setHttpResponseListener(new OnHttpResponseListener() {
//                            @Override
//                            public void OnHttpResponse(String result) {
//                                Log.i("batavree", "ini result " + result);
//                                try {
//                                    JSONObject resUnload = new JSONObject(result);
//                                    if (resUnload.getBoolean("r")){
//                                        Toast.makeText(getActivity(), resUnload.getString("m"), Toast.LENGTH_LONG).show();
//                                        selectedWorkOrder.setStatus(resUnload.getJSONObject("d").getString("status"));
//                                        status.setText("Status : " + selectedWorkOrder.getStatus());
                                        status.setText("Status : menunggu antrian");
                                        charge.setText("Nama Barang : Kayu 3 ton");
                                        try {
                                            vehicleNo.setText("Plat Nomor : " + selectedWorkOrder.getVehicle().getString("police_no"));
                                        }catch (JSONException jsonEx){
                                            Log.e("batavree", "error" + jsonEx.getMessage());
                                        }
                                        spinner.setVisibility(View.INVISIBLE);
                                        btnStart.setText(" Loading ");
                                        btnStart.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogLoading();
                                            }
                                        });
                                    }
//                                } catch (JSONException jss){
//                                    Log.e("batavree", jss.getMessage());
//                                }
//                            }
//                        });
//                        getActivity().stopService(new Intent(getActivity(), AppDataService.class));
//                        FinishJobFragment finishJobFragment = new FinishJobFragment();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fl_container, finishJobFragment);
//                        fragmentTransaction.commit();

//                    }
                });
        alertDialog.setNegativeButton("TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alertDialog.show();

        return;
    }


    public void dialogTakePhoto() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Ambil Gambar");
        alertDialog.setMessage("Ambil gambar untuk memulai perjalanan");
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
                                Log.i("batavree", "loading response " + response);
                                try {
                                    JSONObject resStart = new JSONObject(response);
                                    if (resStart.getBoolean("r")){
                                        Log.i("batavree", "loading fragment " + resStart.getJSONObject("d").toString());
                                        selectedWorkOrder.setStatus(resStart.getJSONObject("d").getString("status"));
                                        Log.i("batavree", "take photo " + selectedWorkOrder.getStatus());
//                                        status.setText("Status : " + appDelfree.getWorkOrders().get(appDelfree.getSelectedWo()).getStatus());
                                    }
                                } catch (JSONException jss){
                                    Log.e("batavree", jss.getMessage());
                                }
                            }
                        });
                        TakePhotoAfterLoading takePhotoAfterLoading= new TakePhotoAfterLoading();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fl_container, takePhotoAfterLoading);
                        fragmentTransaction.commit();
                    }
//                                } catch (JSONException jss){
//                                    Log.e("batavree", jss.getMessage());
//                                }
//                            }
//                        });
//                        getActivity().stopService(new Intent(getActivity(), AppDataService.class));
//                        FinishJobFragment finishJobFragment = new FinishJobFragment();
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fl_container, finishJobFragment);
//                        fragmentTransaction.commit();

//                    }
                });
        alertDialog.setNegativeButton("TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alertDialog.show();

        return;
    }
}
