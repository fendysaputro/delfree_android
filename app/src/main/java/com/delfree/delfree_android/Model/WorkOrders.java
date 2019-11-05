package com.delfree.delfree_android.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by phephen on 06/09/2019
 */

public class WorkOrders implements Serializable {

    private JSONArray WODetails;
    private String WONumber;
    private String WODate;
    private JSONObject driver;
    private String refNo;
    private String shipmentNum;

    public JSONArray getWODetails() {
        return WODetails;
    }

    public void setWODetails(JSONArray WODetails) {
        this.WODetails = WODetails;
    }

    public String getWONumber() {
        return WONumber;
    }

    public void setWONumber(String WONumber) {
        this.WONumber = WONumber;
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
}
