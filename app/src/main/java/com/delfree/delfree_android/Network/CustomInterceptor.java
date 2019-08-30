package com.delfree.delfree_android.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("User-Agent", "Your-App-Name")
                .header("Accept", "application/json")
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
