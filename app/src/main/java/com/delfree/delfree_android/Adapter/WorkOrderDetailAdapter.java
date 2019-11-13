package com.delfree.delfree_android.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import com.delfree.delfree_android.Fragment.FinishJobFragment;
import com.delfree.delfree_android.Model.WorkOrderDetails;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;
import com.google.gson.JsonIOException;

import org.json.JSONException;

import java.util.ArrayList;

import static com.delfree.delfree_android.MainActivity.ShowFragment;

public class WorkOrderDetailAdapter extends ArrayAdapter {

    private ArrayList<WorkOrderDetails> myListJobsByWo;
    private Context context;
    AppDelfree appDelfree;
    Intent mServiceIntent;
    public AppDataService appDataService;

    public WorkOrderDetailAdapter(Context context, int textViewResourceId, ArrayList<WorkOrderDetails> myListJobsByWo) {

        super(context, textViewResourceId, myListJobsByWo);

        this.myListJobsByWo = myListJobsByWo;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_detailjob_adapter, null);

        appDelfree = new AppDelfree();
        mServiceIntent = new Intent(context, appDelfree.getClass());

        appDataService = new AppDataService();
        mServiceIntent = new Intent(context, appDataService.getClass());

        for (int i = 0; i < myListJobsByWo.size(); i++) {
            try {
                TextView listOrder = (TextView) view.findViewById(R.id.tv);
                listOrder.setText("Rute " + myListJobsByWo.get(position).getRoutes().getJSONObject(i).getString("order"));
                TextView routeSrc = (TextView) view.findViewById(R.id.tvRouteSrc);
                routeSrc.setText("Alamat dari : " + myListJobsByWo.get(position).getRoutes().getJSONObject(i).getJSONObject("src").getString("addr"));
                TextView routeDest = (TextView) view.findViewById(R.id.tvRouteDest);
                routeDest.setText("Alamat ke : " + myListJobsByWo.get(position).getRoutes().getJSONObject(i).getJSONObject("dest").getString("addr"));
            } catch (JSONException jexx) {
                jexx.printStackTrace();
            }
        }

        ImageButton moreBtn = (ImageButton) view.findViewById(R.id.iconList);
        moreBtn.setOnClickListener(new View.OnClickListener() {
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
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                        FinishJobFragment finishJobFragment = new FinishJobFragment();
                        Activity activity = (Activity) context;
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        ShowFragment(R.id.fl_container, finishJobFragment,fragmentManager);
//                        context.startService(new Intent(context, AppDataService.class));
                    }
                });
        alertDialog.setNegativeButton("NO",
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
