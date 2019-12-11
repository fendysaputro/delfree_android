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
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import org.json.JSONException;


public class LoadingFragment extends Fragment {

    AppDelfree appDelfree;
    TextView status, charge, vehicleNo;
    Button btnStart;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        Drawable logo = getResources().getDrawable(R.drawable.logobatavree_new);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setLogo(logo);

        status = (TextView) view.findViewById(R.id.tvStatus);
        status.setText("Status : " + appDelfree.getWorkOrders().getStatus());

        charge = (TextView) view.findViewById(R.id.tvCharge);
        charge.setText("Nama Barang : Kayu 3 ton");

        try {
            vehicleNo = (TextView) view.findViewById(R.id.tvVehicleNo);
            vehicleNo.setText("Plat Nomor : " + appDelfree.getWorkOrders().getVehicle().getString("police_no"));
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
}
