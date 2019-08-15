package com.delfree.delfree_android.Model;

import java.util.Date;

public class Tracking {

    private Integer id;
    private String date;
    private double location_lat;
    private double location_long;

    public Tracking(Integer id, String date, double location_lat, double location_long){
        this.id = id;
        this.date = date;
        this.location_lat = location_lat;
        this.location_long = location_long;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocation_long() {
        return location_long;
    }

    public void setLocation_long(double location_long) {
        this.location_long = location_long;
    }
}
