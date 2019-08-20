package com.delfree.delfree_android.Network;

import com.delfree.delfree_android.Model.Driver;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("/Driver")
    Call<Driver> getData ();

    @POST("Driver/authenticate")
    @FormUrlEncoded
    Call<Driver> driverLogin (@Field("phone") String phone,
                              @Field("password") String password);
}
