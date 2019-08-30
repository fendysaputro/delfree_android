package com.delfree.delfree_android.Network;

import com.delfree.delfree_android.Model.Driver;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @GET("/driver")
    Call<Driver> getData ();

    @Headers({
            "Content-Type: application/json;charset=utf-8",
            "Cache-Control: max-age=640000"
    })
    @POST("driver/authenticate")
    @FormUrlEncoded
//    Call<JsonObject> driverLogin(@Field(value = "phone") String phone, @Field(value = "password") String password);
//    @Headers({"Accept: application/json"})

//    login(@Field(value = "_username") String username, @Field(value = "_password") String password);

    Call<ResponseBody> driverLogin (@Field("phone") String phone,
                                    @Field("password") String password);

//    @GET("drive/authenticate/{username}/{password}")
//    Call driverLogin(@Path("phone") String phone, @Path("password") String password);
}
