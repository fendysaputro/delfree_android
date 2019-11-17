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
    private String sim_number;
    @SerializedName("sim_expire")
    private String sim_expire;
    @SerializedName("token")
    private String token;
    @SerializedName("message")
    private String message;

    public Driver(String name, String phone, String address, String sim_number, String sim_expire, String token){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.sim_number = sim_number;
        this.sim_expire = sim_expire;
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

    public String getSim_number() {
        return sim_number;
    }

    public void setSim_number(String sim_number) {
        this.sim_number = sim_number;
    }

    public String getSim_expire() {
        return sim_expire;
    }

    public void setSim_expire(String sim_expire) {
        this.sim_expire = sim_expire;
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
                        ",simNumber = '" + sim_number + '\'' +
                        ",simExpired = '" + sim_expire + '\'' +
                        ",token = '" + token + '\'' +
                        "}";
    }

}
