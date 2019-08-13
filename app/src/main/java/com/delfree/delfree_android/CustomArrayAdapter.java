package com.delfree.delfree_android;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter {

    private String[] myListJobs;
    private Context context;

    public CustomArrayAdapter(Context context, int textViewResourceId, String[] myListJobs) {

        super(context, textViewResourceId, myListJobs);

        this.myListJobs = myListJobs;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText(myListJobs[position]);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "ini toast", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    public static void ShowFragment(int resId, Fragment fragment, android.support.v4.app.FragmentManager fm) {
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(resId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
