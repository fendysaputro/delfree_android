package com.delfree.delfree_android.Network;

public class ApiUtils {

    private ApiUtils (){}

    public static final String baseUrl = "http://api.batavree.com/apis/v1/";

    public static APIService getAPIService () {
        return RetrofitClient.getClient(baseUrl).create(APIService.class);
    }

}
