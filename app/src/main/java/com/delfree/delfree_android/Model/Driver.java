package com.delfree.delfree_android.Model;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by phephen on 6/8/19.
 */

public class Driver {

    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("sim_number")
    private String simNumber;
    @SerializedName("sim_expired")
    private String simExpired;
    @SerializedName("token")
    private String token;
    @SerializedName("message")
    private String message;

    public Driver(String name, String phone, String address, String simNumber, String simExpired, String token){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.simNumber = simNumber;
        this.simExpired = simExpired;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    public String getSimExpired() {
        return simExpired;
    }

    public void setSimExpired(String simExpired) {
        this.simExpired = simExpired;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return
                "Driver{" +
                        "name = '" + name + '\'' +
                        ",phone = '" + phone + '\'' +
                        ",address = '" + address + '\'' +
                        ",simNumber = '" + simNumber + '\'' +
                        ",simExpired = '" + simExpired + '\'' +
                        ",token = '" + token + '\'' +
                        "}";
    }

}

