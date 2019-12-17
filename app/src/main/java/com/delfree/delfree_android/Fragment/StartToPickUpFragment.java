package com.delfree.delfree_android.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.R;

import org.json.JSONException;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_to_pick_up, container, false);

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

        WorkOrders selectedWorkOrder = appDelfree.getWorkOrders().get(appDelfree.getSelectedWo());

        WONumber = (TextView) view.findViewById(R.id.detail_job);
        WONumber.setText(selectedWorkOrder.getWONum());

        status = (TextView) view.findViewById(R.id.tvStatus);
        status.setText("Status : Menuju Pick up");

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

        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerJobs);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String statusLoad = parent.getItemAtPosition(position).toString();
                Log.i("batavree", "status position " + statusLoad);
//                appMms.setOpenTicket(statusLoad);
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
//                dialog();
            }
        });

        return view;
    }
}
