package com.uas.mobile.api.ws;

import com.uas.mobile.model.ModelUserResponse;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiEmployeeInterface {

    @GET
    Call<ModelUserResponse> getDetailEmployee(@Url String url, @Query("nim") String nim, @Query("nama") String nama);

    @GET("null")
    Call<ModelUserResponse> getAllData(@Query("nim") String nim, @Query("nama") String nama);

}
