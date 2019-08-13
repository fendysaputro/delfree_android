package com.delfree.delfree_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CustomArrayAdapter extends ArrayAdapter {

    private String[] myImageArray;
    private Context context;

    public CustomArrayAdapter(Context context, int textViewResourceId, String[] myImageArray) {

        super(context, textViewResourceId, myImageArray);

        this.myImageArray = myImageArray;
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view ;

        view = inflater.inflate(R.layout.custom_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv);

        textView.setText(myImageArray[position]);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked : "+ myImageArray[position], Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
