package com.delfree.delfree_android;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.delfree.delfree_android.Model.Driver;
import com.delfree.delfree_android.Model.WorkOrderDetails;
import com.delfree.delfree_android.Model.WorkOrders;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.media.MediaCodec.MetricsConstants.MODE;

/**
 * Created by phephen on 6/8/19.
 */

public class AppDelfree extends Application {

    boolean login;
    Driver driver;
    File imageFile;
    private Bitmap image;
    boolean picture;
    double latitude;
    double longitude;
    String Wo_Number;
    String shipmentNumber;
    WorkOrders workOrders;
    WorkOrderDetails workOrderDetails;
    public static String HOST = "http://api.batavree.com/apis/v1/";
    public static String LOGIN_PATH = "driver/authenticate";
    public static String SEND_LOC = "log/vehicle";
    public static String PICTURE_PATH = "delfree/pictures/";
    public static String LOADING_PATH = "log/goodsload";
    public static String UNLOADING_PATH = "log/goodsunload";
    public static String WO = "wo";


    public boolean isLogin(Activity activity, int MODE) {
        SharedPreferences sharedPref = activity.getPreferences(MODE);
        String driverString = sharedPref.getString("batavree", "");
        if(driverString.isEmpty()){
            login = false;
        } else {
            login = true;
            try {
                JSONObject driverObj = new JSONObject(driverString);
                driver = new Driver(driverObj.getString("_id"),
                        driverObj.getString("name"),driverObj.getString("phone"),
                        driverObj.getString("address"),driverObj.getString("sim_number"),
                        driverObj.getString("sim_expire"),driverObj.getString("token"));
            } catch (JSONException es){
                Log.e("batavree", es.getMessage());
            }
        }
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isPicture() {
        return picture;
    }

    public void setPicture(boolean picture) {
        this.picture = picture;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public WorkOrders getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(WorkOrders workOrders) {
        this.workOrders = workOrders;
    }

    public WorkOrderDetails getWorkOrderDetails() {
        return workOrderDetails;
    }

    public void setWorkOrderDetails(WorkOrderDetails workOrderDetails) {
        this.workOrderDetails = workOrderDetails;
    }


}



