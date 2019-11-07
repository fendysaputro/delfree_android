package com.delfree.delfree_android.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.delfree.delfree_android.Fragment.FinishJobFragment;
import com.delfree.delfree_android.Model.WorkOrderDetails;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

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

//        TextView textView = (TextView) view.findViewById(R.id.tv);
//        textView.setText(myListJobsByWo.get(position).getWODetails().getJSONObject(0).getString("id"));
//        Log.i("batavree", "wo_Detail " + myListJobsByWo.get(position).getWODetails().getJSONObject(0).getString("id"));


        ImageButton moreBtn = (ImageButton) view.findViewById(R.id.iconList);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinishJobFragment finishJobFragment = new FinishJobFragment();
                Activity activity = (Activity) context;
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                ShowFragment(R.id.fl_container, finishJobFragment,fragmentManager);
                context.startService(new Intent(context, AppDataService.class));
            }
        });

        return view;
    }

}
