package com.delfree.delfree_android.Service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.delfree.delfree_android.AppDelfree;
import com.delfree.delfree_android.DbHelper;
import com.delfree.delfree_android.MainActivity;
import com.delfree.delfree_android.Model.Tracking;
import com.delfree.delfree_android.Network.AsyncHttpTask;
import com.delfree.delfree_android.Network.OnHttpResponseListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.http.POST;

/**
 * Created by phephen on 6/8/19
 */


public class AppDataService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private static final String LOGSERVICE = "#######";
    private long UPDATE_INTERVAL = 10 * 1000;  /* 1 minutes */
    private long FASTEST_INTERVAL = 10000; /* 1 minutes */
    double lati = 0;
    double longi = 0;
    private LocationManager mlocationManager;
    private String provider;
    DbHelper dB;
    AppDelfree appDelfree;
    private long INTERVAL_SEND_DATA = 0;
    private FusedLocationProviderClient mFusedLocationClient;
    String driverId, vehicleId, woId;
    int i = 0;
//    public static ArrayList<Tracking> tracks;

    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1000); // schedule next wake up every second
            Intent notificationIntent = new Intent(AppDataService.this, AppDataService.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(AppDataService.this, 0, notificationIntent, 0);
            AlarmManager keepAwake = (AlarmManager) getSystemService(ALARM_SERVICE);
            keepAwake.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);

            long current = System.currentTimeMillis();

            if ((current-current%1000)%(1000*300)  == 0) { // record on every tenth seconds (0s, 10s, 20s, 30s...) 5 minutes = 300 seconds
                Log.i("Batavree", "this is periodic");
//                Log.i("Batavree", dB.getAllTracking().toString());
                final ArrayList<Tracking> trackingList = dB.getAllTracking();
                int trackingSize = trackingList.size();
                for (i = 0; i < trackingSize; i++) {
                    final Tracking tracking = trackingList.get(i);
                    AsyncHttpTask sendData = new AsyncHttpTask("woid=" + woId +
                        "&driverid=" + driverId +
                        "&vehicleid=" + vehicleId +
                        "&lang=" + tracking.getLocation_lat() +
                        "&long=" + tracking.getLocation_long(), getApplicationContext());
                sendData.execute(appDelfree.HOST + appDelfree.SEND_LOC, "POST");
                sendData.setHttpResponseListener(new OnHttpResponseListener() {
                    @Override
                    public void OnHttpResponse(String response) {
                        try {
                            JSONObject resObj = new JSONObject(response);
                            if (resObj.getBoolean("r")){
                                Toast.makeText(getApplication(), resObj.getString("m"), Toast.LENGTH_LONG).show();
                                dB.deleteById(tracking.getId());
                                Log.i("Batavree", "ini response send to server " + resObj);
                            }
                        } catch (JSONException jex){
                            jex.printStackTrace();
                        }
                    }
                });
                }
            }
        }
    };

    public void getData () {
        try {
            vehicleId = appDelfree.getSelectedWo().getVehicle().getString("_id");
            driverId = appDelfree.getDriver().getId();
            woId = appDelfree.getSelectedWo().getId();
        } catch (JSONException jes){
            jes.getMessage();
        }
    }

    public void stopHandler() {
        handler.removeCallbacks(periodicUpdate);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        appDelfree = (AppDelfree) getApplicationContext();

        buildGoogleApiClient();
        Log.i(LOGSERVICE, "onCreate");
        getData();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOGSERVICE, "onStartCommand");

        handler.post(periodicUpdate);
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
        return START_STICKY;
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Log.i(LOGSERVICE, "onConnected" + bundle);

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLocation != null) {
            getLastLocation();
            Log.i(LOGSERVICE, "lat " + mLocation.getLatitude());
            Log.i(LOGSERVICE, "lng " + mLocation.getLongitude());
        }

        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOGSERVICE, "onConnectionSuspended " + i);

    }

    @Override
    public void onLocationChanged(Location location) {
//        Log.i(LOGSERVICE, "latitude " + location.getLatitude());
//        Log.i(LOGSERVICE, "longitude " + location.getLongitude());
        LatLng mLocation = new LatLng(location.getLatitude(), location.getLongitude());

        lati = location.getLatitude();
        longi = location.getLongitude();

        appDelfree.setLatitude(lati);
        appDelfree.setLongitude(longi);

        dB = new DbHelper(this);
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
        dB.insertTracking(date, lati, longi);

        Toast.makeText(this, "ini mLocation " + mLocation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mlocationManager != null){
            stopLocationUpdate();
            Log.i(LOGSERVICE, "Service Stop");
        }
        stopHandler();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOGSERVICE, "onConnectionFailed ");

    }

    @SuppressLint("RestrictedApi")
    private void initLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void getLastLocation(){
        mlocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = mlocationManager.getBestProvider(criteria, false);
        Log.i("Batavree", "ini provider " + provider);
        if(provider == null){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            return;
        }

        Location location = null;
        try {
            location = mlocationManager.getLastKnownLocation(provider);
        } catch (SecurityException e){

        }
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Log.i("Batavree", "Location not available");
        }
    }

    private void startLocationUpdate() {
        initLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }
}