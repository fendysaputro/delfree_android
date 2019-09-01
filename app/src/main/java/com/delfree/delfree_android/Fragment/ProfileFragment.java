package com.delfree.delfree_android.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.R;


/**
 * Created by phephen on 6/8/19.
 */

public class ProfileFragment extends Fragment {

    AppDelfree appDelfree;
    TextView driverName, simNumber, driverAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        driverName = (TextView) view.findViewById(R.id.driver_name);
        String name = appDelfree.getDriver().getName();
        driverName.setText("Name : " + name);

        simNumber = (TextView) view.findViewById(R.id.sim_no);
        String sim = appDelfree.getDriver().getSim_number();
        simNumber.setText("SIM No : " + sim);

        driverAddress = (TextView) view.findViewById(R.id.address);
        String addr = appDelfree.getDriver().getAddress();
        driverAddress.setText("Address : " + addr);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Back");
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
}
