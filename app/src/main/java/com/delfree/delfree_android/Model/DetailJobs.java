package com.delfree.delfree_android.Model;

import java.io.Serializable;

public class DetailJobs implements Serializable {

    private String dropPoint;
    private String listJobs;

    public String getDropPoint() {
        return dropPoint;
    }

    public void setDropPoint(String dropPoint) {
        this.dropPoint = dropPoint;
    }

    public String getListJobs() {
        return listJobs;
    }

    public void setListJobs(String listJobs) {
        this.listJobs = listJobs;
    }
}
