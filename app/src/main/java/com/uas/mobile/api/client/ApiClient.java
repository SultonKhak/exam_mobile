package com.uas.mobile.api.client;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/stage2/people/";
    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if (retrofit==null) {
            final long timeout = 360;
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.callTimeout(timeout, TimeUnit.SECONDS);
            builder.connectTimeout(timeout, TimeUnit.SECONDS);
            builder.readTimeout(timeout, TimeUnit.SECONDS);
            builder.writeTimeout(timeout, TimeUnit.SECONDS);
            final OkHttpClient httpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

}
