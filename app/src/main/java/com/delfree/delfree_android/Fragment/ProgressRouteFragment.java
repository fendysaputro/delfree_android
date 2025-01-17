package com.delfree.delfree_android.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.delfree.delfree_android.MainActivity.ShowFragment;

public class ProgressRouteFragment extends Fragment {
    AppDelfree appDelfree;
    private ListView progressList;
    ArrayList<WorkOrderDetails> list = null;
    Button btnFinish;
    TextView statusProgress, addressFrom, addressTo, woNumber;
    private Context context;
    String driverId, vehicleId, woId;
    double latitude = 0;
    double longitude = 0;
    WorkOrders selectedWorkOrder;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_route, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        Drawable logo = getResources().getDrawable(R.drawable.logobatavree_new);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setLogo(logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));

        selectedWorkOrder = appDelfree.getSelectedWo();

        woNumber = (TextView) view.findViewById(R.id.wo_number);
        woNumber.setText(selectedWorkOrder.getWONum());

        statusProgress = (TextView) view.findViewById(R.id.tvStatus);
        statusProgress.setText("Status : " + selectedWorkOrder.getStatus());

//        addressFrom = (TextView) view.findViewById(R.id.tvRouteSrc);
//        addressFrom.setText("Nama Barang : Kayu 3 ton");
//
//        try {
//            addressTo = (TextView) view.findViewById(R.id.tvRouteDest);
//            addressTo.setText("Plat Nomor : " + selectedWorkOrder.getVehicle().getString("police_no"));
//        }catch (JSONException jsonEx){
//            Log.e("batavree", "error" + jsonEx.getMessage());
//        }

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
                appDelfree.setWorkOrderDetails(wod);

                addressFrom = (TextView) view.findViewById(R.id.tvRouteSrc);
                addressFrom.setText("Alamat dari : " + appDelfree.getWorkOrderDetails().getRoutes().getJSONObject(i).getJSONObject("src").getString("addr"));

                addressTo = (TextView) view.findViewById(R.id.tvRouteDest);
                addressTo.setText("Alamat ke : " + appDelfree.getWorkOrderDetails().getRoutes().getJSONObject(i).getJSONObject("dest").getString("addr"));

            } catch (JSONException ex){
                ex.printStackTrace();
            }
        }

        spinner = (Spinner) view.findViewById(R.id.spinnerJobs);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String statusLoad = parent.getItemAtPosition(position).toString();
                Log.i("batavree", "status position " + statusLoad);
//                status.setText("Status : " + statusLoad);
                btnFinish.setText(statusLoad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final List<String> loadStatus = new ArrayList<>();
        loadStatus.add(" Bongkar Barang ");
        loadStatus.add(" Menunggu Antrian ");

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, loadStatus);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(statusAdapter);

        btnFinish = (Button) view.findViewById(R.id.buttonFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnFinish.getText().equals(" Bongkar Barang ")){
                    dialogBongkar();
                } else {
                    dialogWaitUnloading();
                }
            }
        });

        return view;
    }

    public JSONArray getWODetails (){

        return selectedWorkOrder.getWODetails();
    }

    public void dialogBongkar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Menurunkan Muatan");
        alertDialog.setMessage("Apakah anda yakin untuk menurunkan muatan?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncHttpTask unloadTask = new AsyncHttpTask("woid=" + woId +
                                "&driverid=" + driverId +
                                "&vehicleid=" + vehicleId +
                                "&lang=" + latitude +
                                "&long=" + longitude, getContext());
                        unloadTask.execute(appDelfree.HOST + appDelfree.UNLOADING_PATH, "POST");
                        unloadTask.setHttpResponseListener(new OnHttpResponseListener() {
                            @Override
                            public void OnHttpResponse(String response) {
                                try {
                                    JSONObject resWo = new JSONObject(response);
                                    if (resWo.getBoolean("r")){
                                        Toast.makeText(getActivity(), resWo.getString("m"), Toast.LENGTH_LONG).show();
                                        selectedWorkOrder.setStatus(resWo.getJSONObject("d").getString("status"));
                                        statusProgress.setText("Status : " + selectedWorkOrder.getStatus());
                                        addressFrom.setText("Nama Barang : Kayu 3 ton");
                                        try {
                                            addressTo.setText("Plat Nomor : " + selectedWorkOrder.getVehicle().getString("police_no"));
                                        }catch (JSONException jsonEx){
                                            Log.e("batavree", "error" + jsonEx.getMessage());
                                        }
                                        spinner.setVisibility(View.INVISIBLE);
                                        btnFinish.setText("Selesai");
                                        btnFinish.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogFinish();
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

    public void dialogFinish() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Selesai Perjalanan");
        alertDialog.setMessage("Apakah anda yakin mengakhiri perjalanan?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AsyncHttpTask toUnloadTask = new AsyncHttpTask("woid=" + woId +
                                "&driverid=" + driverId +
                                "&vehicleid=" + vehicleId +
                                "&lang=" + latitude +
                                "&long=" + longitude, getContext());
                        toUnloadTask.execute(appDelfree.HOST + appDelfree.FINISH_PATH, "POST");
                        toUnloadTask.setHttpResponseListener(new OnHttpResponseListener() {
                            @Override
                            public void OnHttpResponse(String result) {
                                Log.i("batavree", "ini result " + result);
                                try {
                                    JSONObject resUnload = new JSONObject(result);
                                    if (resUnload.getBoolean("r")){
                                        Toast.makeText(getActivity(), resUnload.getString("m"), Toast.LENGTH_LONG).show();
                                        appDelfree.getSelectedWo().setStatus(resUnload.getJSONObject("d").getString("status"));
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
                        Toast.makeText(getActivity(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alertDialog.show();

        return;
    }


    public void dialogWaitUnloading() {
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
                        statusProgress.setText("Status : menunggu antrian");
                        addressFrom.setText("Nama Barang : Kayu 3 ton");
                        try {
                            addressTo.setText("Plat Nomor : " + selectedWorkOrder.getVehicle().getString("police_no"));
                        }catch (JSONException jsonEx){
                            Log.e("batavree", "error" + jsonEx.getMessage());
                        }
                        spinner.setVisibility(View.INVISIBLE);
                        btnFinish.setText(" UnLoading ");
                        btnFinish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBongkar();
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

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.allowBackPressed = false;
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        statusProgress.setText("Status : " + selectedWorkOrder.getStatus());
    }
}
