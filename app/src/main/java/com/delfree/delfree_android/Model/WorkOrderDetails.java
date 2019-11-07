package com.delfree.delfree_android.Model;

import org.json.JSONArray;

import java.io.Serializable;

public class WorkOrderDetails implements Serializable {

    private JSONArray routes;

    public JSONArray getRoutes() {
        return routes;
    }

    public void setRoutes(JSONArray routes) {
        this.routes = routes;
    }
}
