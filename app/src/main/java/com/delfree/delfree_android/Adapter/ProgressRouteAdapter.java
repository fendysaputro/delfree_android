package com.delfree.delfree_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import java.util.ArrayList;

public class ProgressRouteAdapter extends ArrayAdapter {

//    private ArrayList<WorkOrders> myProgressList;
    private String[] myProgressList;
    private Context context;
    Intent mServiceIntent;
    public AppDataService appDataService;
    AppDelfree appDelfree;

    public ProgressRouteAdapter(Context context, int textViewResourceId, String[] myProgressList) {
        super(context, textViewResourceId, myProgressList);

        this.myProgressList = myProgressList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item_progress_adapter, null);

        appDataService = new AppDataService();
        mServiceIntent = new Intent(context, appDataService.getClass());

        appDelfree = new AppDelfree();
        mServiceIntent = new Intent(context, appDelfree.getClass());

        TextView textView = (TextView) view.findViewById(R.id.tv);
//        textView.setText(myProgressList.get(position).getWONum());
        textView.setText(myProgressList[position]);

//        TextView items = (TextView) view.findViewById(R.id.tvItems);
//        items.setText("krupuk");
//
//        TextView driver = (TextView) view.findViewById(R.id.tvDriver);
//        driver.setText("fendy");
//        driver.setText(appDelfree.getDriver().getName());

        return view;
    }
}
