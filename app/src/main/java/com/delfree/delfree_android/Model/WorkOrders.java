package com.delfree.delfree_android.Model;

import java.io.Serializable;

/**
 * Created by phephen on 06/09/2019
 */

public class WorkOrders implements Serializable {

    private String WO_number;

    public String getWO_number() {
        return WO_number;
    }

    public void setWO_number(String WO_number) {
        this.WO_number = WO_number;
    }
}
