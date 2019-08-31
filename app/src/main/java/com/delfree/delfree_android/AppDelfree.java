package com.delfree.delfree_android;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.delfree.delfree_android.Model.Driver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.media.MediaCodec.MetricsConstants.MODE;

/**
 * Created by phephen on 6/8/19.
 */

public class AppDelfree extends Application {

    private String baseUrl = "";
    boolean login;
    Driver driver;
    File imageFile;
    private Bitmap image;
    boolean picture;
    public static String HOST = "http://api.batavree.com/apis/v1/";
    public static String LOGIN_PATH = "driver/authenticate";


    public boolean isLogin(Activity activity, int MODE) {

        SharedPreferences sharedPref = activity.getPreferences(MODE);
        String driverString = sharedPref.getString("batavree", "");
        if(driverString.isEmpty()){
            login = false;
        } else {
            login = true;
            try {
                JSONObject driverObj = new JSONObject(driverString);
                driver = new Driver(driverObj.getString("name"),driverObj.getString("phone"),
                        driverObj.getString("address"),driverObj.getString("simNumber"),
                        driverObj.getString("simExpire"),driverObj.getString("token"));
            } catch (JSONException es){
                Log.e("amg", es.getMessage());
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
}


