package com.delfree.delfree_android.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.delfree.delfree_android.Model.Tracking;

import java.util.ArrayList;

public class TrackingAdapter extends ArrayAdapter<Tracking> {

    private final ArrayList<Tracking> values;
    private final Context context;

    public TrackingAdapter(Context context, ArrayList<Tracking> values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    public void replaceItem(int position, Tracking newItem){
        values.set(position, newItem);
    }

    @Nullable
    @Override
    public Tracking getItem(int position){
        return super.getItem(position);
    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}

