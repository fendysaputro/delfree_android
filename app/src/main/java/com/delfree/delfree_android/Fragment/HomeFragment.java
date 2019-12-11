package com.delfree.delfree_android.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.delfree.delfree_android.Adapter.HomeAdapter;
import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.DbHelper;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Model.WorkOrders;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.delfree.delfree_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by phephen on 6/8/19.
 */

public class HomeFragment extends Fragment {

    private ListView listJobs;
    Button button;
    AppDelfree appDelfree;
    private boolean isBackPressedToExit;
    ArrayList<WorkOrders> list = null;
    HomeAdapter adapter = null;
    private LocationManager locationManager;
    private String provider;
    Location location;
    double longi;
    double lati;
    String address, city, state = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        requestPermission();
        appDelfree = (AppDelfree) getActivity().getApplication();

        Drawable logo = getResources().getDrawable(R.drawable.logobatavree_new);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setLogo(logo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.chooseNav));

        listJobs=(ListView) view.findViewById(R.id.list);
        list = new ArrayList<WorkOrders>();
        adapter = new HomeAdapter(getContext(), R.layout.custom_item_home_adapter, list);
        listJobs.setAdapter(adapter);

        getDataWO("", list, adapter);
        getLastLocation();
        return view;
    }

    public void getDataWO (String data, final ArrayList<WorkOrders> list, final HomeAdapter adapter){
        getData(data, list, adapter);
    }

    private void getData (String data, final ArrayList<WorkOrders> list, final HomeAdapter adapter){
        AsyncHttpTask woHttp = new AsyncHttpTask(data, getActivity());
        woHttp.execute(appDelfree.HOST + appDelfree.WO, "GET");
        woHttp.setHttpResponseListener(new OnHttpResponseListener() {
            @Override
            public void OnHttpResponse(String response) {
//                Log.i("batavree ", "ini response " + response);
                try {
                    JSONObject resOBJ = new JSONObject(response);
                    if (resOBJ.getBoolean("r")){
                        JSONArray woArray = resOBJ.getJSONArray("d");
//                        Log.i("batavree", "workorder " + woArray.toString());
                        for (int i = 0; i < woArray.length(); i++) {
                            JSONObject WO = woArray.getJSONObject(i);
                            WorkOrders woOrders = new WorkOrders();
                            woOrders.setId(WO.getString("_id"));
                            woOrders.setWODetails(WO.getJSONArray("WODetails"));
                            woOrders.setWONum(WO.getString("WONum"));
                            woOrders.setWODate(WO.getString("WODate"));
                            woOrders.setDriver(WO.getJSONObject("driver"));
                            woOrders.setVehicle(WO.getJSONObject("vehicle"));
                            woOrders.setRefNo(WO.getString("refNo"));
                            woOrders.setShipmentNum(WO.getString("shipmentNum"));
                            woOrders.setStatus(WO.getString("status"));
                            list.add(woOrders);
                            appDelfree.setWorkOrders(woOrders);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity.allowBackPressed = false;
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    private void requestPermission () {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_NETWORK_STATE)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 3);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CHANGE_NETWORK_STATE)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CHANGE_NETWORK_STATE}, 4);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.INTERNET}, 5);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 6);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA}, 7);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.RECORD_AUDIO}, 8);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
        }
    }

    public void getLastLocation(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Log.i("newmms", "ini provider " + provider);
        if (provider == null){
            requestPermission();
            return;
        }
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e){

        }
        if (location != null){
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Log.i("batavree", "Location not available");
//            nextButton.setEnabled(false);
        }
    }

    public void onLocationChanged(Location location){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        lati = location.getLatitude();
        appDelfree.setLatitude(lati);
        longi = location.getLongitude();
        appDelfree.setLongitude(longi);

        try {
            Log.e("latitude", "inside latitude--" + appDelfree.getLatitude());
            Log.e("longitude", "inside longitude--" + appDelfree.getLongitude());
            addresses = geocoder.getFromLocation(appDelfree.getLatitude(), appDelfree.getLongitude(), 1);

            if (addresses != null && addresses.size() > 0){
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();

            }
        } catch (IOException e){
            e.printStackTrace();
        }
        this.location = location;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length == 1 && permissions[0] == Manifest.permission_group.CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
        } else {
        }
    }
}
