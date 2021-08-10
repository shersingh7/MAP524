package com.dv.test.network;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("https://restcountries.eu/rest/v2/all")
    Call<JsonArray> Countrydata();
}
