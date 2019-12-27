package com.delfree.delfree_android.Adapter;

import android.app.Activity;
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
import com.delfree.delfree_android.Fragment.BaseFragment;
import com.delfree.delfree_android.Fragment.DetailJobFragment;
import com.delfree.delfree_android.Fragment.LoadingFragment;
import com.delfree.delfree_android.Fragment.ProgressRouteFragment;
import com.delfree.delfree_android.Fragment.StartToPickUpFragment;
import com.delfree.delfree_android.Fragment.UnloadingFragment;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.delfree.delfree_android.MainActivity.ShowFragment;

public class HistoryAdapter extends ArrayAdapter {

    private ArrayList<WorkOrders> myJobsHistory;
    private Context context;
    Intent mServiceIntent;
    public AppDataService appDataService;
    public boolean mTracking = false;
    Activity activity;
    AppDelfree appDelfree;

    public HistoryAdapter(Context context, int textViewResourceId, ArrayList<WorkOrders> myJobsHistory) {

        super(context, textViewResourceId, myJobsHistory);

        this.myJobsHistory = myJobsHistory;
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

//        final WorkOrders selectedWorkOrder = appDelfree.getWorkOrders().get(appDelfree.getSelectedWo());
//        Log.i("batavree", "ini list job " + myJobsHistory.get(position).getStatus());

        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(myJobsHistory.get(position).getWONum());

        try {
            String WoDate = myJobsHistory.get(position).getWODate();
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
                WorkOrders wo = myJobsHistory.get(position);
                appDelfree.setSelectedWo(wo);
                progressLoading();
            }
        });

        return view;
    }

    public void progressLoading() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Progress");
        alertDialog.setMessage("Pilih YA Untuk Melihat Progress Status?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        BaseFragment baseFragment = new BaseFragment();
                        Activity activity = (Activity) context;
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        ShowFragment(R.id.fl_container, baseFragment, fragmentManager);
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
