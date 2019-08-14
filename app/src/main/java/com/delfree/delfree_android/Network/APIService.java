package com.delfree.delfree_android.Network;

import com.delfree.delfree_android.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("/")
    Call<User> getData ();

}
