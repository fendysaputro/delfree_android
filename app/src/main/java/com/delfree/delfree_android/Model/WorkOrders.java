package com.delfree.delfree_android.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by phephen on 06/09/2019
 */

public class WorkOrders implements Serializable {

    private String id;
    private JSONArray WODetails;
    private String WONum;
    private String WODate;
    private JSONObject driver;
    private JSONObject vehicle;
    private String refNo;
    private String shipmentNum;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONArray getWODetails() {
        return WODetails;
    }

    public void setWODetails(JSONArray WODetails) {
        this.WODetails = WODetails;
    }

    public String getWONum() {
        return WONum;
    }

    public void setWONum(String WONum) {
        this.WONum = WONum;
    }

    public String getWODate() {
        return WODate;
    }

    public void setWODate(String WODate) {
        this.WODate = WODate;
    }

    public JSONObject getDriver() {
        return driver;
    }

    public void setDriver(JSONObject driver) {
        this.driver = driver;
    }

    public JSONObject getVehicle() {
        return vehicle;
    }

    public void setVehicle(JSONObject vehicle) {
        this.vehicle = vehicle;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getShipmentNum() {
        return shipmentNum;
    }

    public void setShipmentNum(String shipmentNum) {
        this.shipmentNum = shipmentNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
