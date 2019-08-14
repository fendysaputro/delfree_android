package com.delfree.delfree_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.delfree.delfree_android.R;

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
