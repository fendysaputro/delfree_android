package com.delfree.delfree_android.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.delfree.delfree_android.DetailJobFragment;
import com.delfree.delfree_android.HistoryFragment;
import com.delfree.delfree_android.R;

import static com.delfree.delfree_android.MainActivity.ShowFragment;

public class HistoryAdapter extends ArrayAdapter {

    private String[] myJobsHistory;
    private Context context;

    public HistoryAdapter(Context context, int textViewResourceId, String[] myJobsHistory) {

        super(context, textViewResourceId, myJobsHistory);

        this.myJobsHistory = myJobsHistory;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(myJobsHistory[position]);

        return view;
    }

}
