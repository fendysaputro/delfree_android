package com.delfree.delfree_android.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delfree.delfree_android.Fragment.DetailJobFragment;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import static com.delfree.delfree_android.MainActivity.ShowFragment;

public class HomeAdapter extends ArrayAdapter {

    private String[] myListJobs;
    private Context context;
    Intent mServiceIntent;
    private AppDataService appDataService;


    public HomeAdapter(Context context, int textViewResourceId, String[] myListJobs) {

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

        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(myListJobs[position]);

        ImageButton moreBtn = (ImageButton) view.findViewById(R.id.iconList);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailJobFragment detailJobFragment = new DetailJobFragment();
                Activity activity = (Activity) context;
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                ShowFragment(R.id.fl_container, detailJobFragment,fragmentManager);
                context.startService(new Intent(context, AppDataService.class));
            }
        });

        return view;
    }
}
