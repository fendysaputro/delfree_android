package com.delfree.delfree_android.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Model.WorkOrderDetails;
import com.delfree.delfree_android.R;
import com.delfree.delfree_android.Service.AppDataService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.delfree.delfree_android.MainActivity.ShowFragment;

public class ProgressRouteFragment extends Fragment {
    AppDelfree appDelfree;
    private ListView progressList;
    ArrayList<WorkOrderDetails> list = null;
    Button btnFinish;
    TextView status, addressFrom, addressTo;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_route, container, false);

        appDelfree = (AppDelfree) getActivity().getApplication();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Batavree");
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));

        JSONArray detailWO = getWODetails();
        for (int i = 0; i < detailWO.length(); i++) {
            try {
                JSONObject WoObj = detailWO.getJSONObject(i);
                WorkOrderDetails wod = new WorkOrderDetails();
                wod.setRoutes(WoObj.getJSONArray("routes"));
                wod.setWONum(WoObj.getString("WONum"));
                appDelfree.setWorkOrderDetails(wod);

                status = (TextView) view.findViewById(R.id.tvStatus);
                status.setText("Status : On Progress");

                addressFrom = (TextView) view.findViewById(R.id.tvRouteSrc);
                addressFrom.setText("Alamat dari : " + appDelfree.getWorkOrderDetails().getRoutes().getJSONObject(i).getJSONObject("src").getString("addr"));

                addressTo = (TextView) view.findViewById(R.id.tvRouteDest);
                addressTo.setText("Alamat ke : " + appDelfree.getWorkOrderDetails().getRoutes().getJSONObject(i).getJSONObject("dest").getString("addr"));

            } catch (JSONException ex){
                ex.printStackTrace();
            }
        }

        btnFinish = (Button) view.findViewById(R.id.buttonFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        return view;
    }

    public JSONArray getWODetails (){

        return appDelfree.getWorkOrders().getWODetails();
    }

    public void dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Selesai Perjalanan");
        alertDialog.setMessage("Apakah anda yakin mengakhiri perjalanan?");
        alertDialog.setPositiveButton("YA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        stopService(new Intent(getActivity(), AppDataService.class));

                        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                        mainActivity.putExtra("fragmentName", "FinishJob");
                        startActivity(mainActivity);

                        Activity activity = (Activity) context;
                        FinishJobFragment finishJobFragment = new FinishJobFragment();
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        ShowFragment(R.id.fl_container, finishJobFragment,fragmentManager);
//                        context.stopService(new Intent(context, AppDataService.class));

                    }
                });
        alertDialog.setNegativeButton("TIDAK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
        alertDialog.show();

        return;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.allowBackPressed = false;
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}