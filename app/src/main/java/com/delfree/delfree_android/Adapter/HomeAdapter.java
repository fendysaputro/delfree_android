package com.delfree.delfree_android.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Fragment.DetailJobFragment;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

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

    public HomeAdapter(Context context, int textViewResourceId, ArrayList<WorkOrders> myListJobs) {

        super(context, textViewResourceId, myListJobs);

        this.myListJobs = myListJobs;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_home_adapter, null);

        appDataService = new AppDataService();
        mServiceIntent = new Intent(context, appDataService.getClass());

        appDelfree = new AppDelfree();
        mServiceIntent = new Intent(context, appDelfree.getClass());

        TextView textView = (TextView) view.findViewById(R.id.tv);
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
                DetailJobFragment detailJobFragment = new DetailJobFragment();
                Activity activity = (Activity) context;
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                ShowFragment(R.id.fl_container, detailJobFragment,fragmentManager);
            }
        });

        return view;
    }
}
