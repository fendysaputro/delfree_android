package com.delfree.delfree_android.Network;

public class ApiUtils {

    private ApiUtils (){}

    public static final String BASE_URL = "http://api.batavree.com/apis/v1/";

    public static APIService getAPIService () {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
