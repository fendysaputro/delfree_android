package com.delfree.delfree_android.Network;

import com.delfree.delfree_android.Model.Driver;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @GET("/driver")
    Call<Driver> getData ();

    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
    })
    @FormUrlEncoded
    @POST("driver/authenticate")
    Call<ResponseBody> driverLogin (@Field("phone") String phone,
                                     @Field("password") String password);
}
