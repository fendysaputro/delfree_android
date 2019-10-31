package com.delfree.delfree_android.Model;

import java.io.Serializable;

/**
 * Created by phephen on 06/09/2019
 */

public class WorkOrders implements Serializable {

    private String WODetails;
    private String WODate;
    private String driver;
    private String refNo;
    private String shipmentNum;

    public String getWODetails() {
        return WODetails;
    }

    public void setWODetails(String WODetails) {
        this.WODetails = WODetails;
    }

    public String getWODate() {
        return WODate;
    }

    public void setWODate(String WODate) {
        this.WODate = WODate;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
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
