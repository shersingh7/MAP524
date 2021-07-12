package com.dv.davinder_weather.network;

import com.dv.davinder_weather.models.LocationContainer;
import com.dv.davinder_weather.models.WeatherContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("http://api.weatherapi.com/v1/current.json?key=4097b874e4714d4faf7112630210907")
    Call<WeatherContainer> getData(@Query("q") String name);

    @GET("http://api.weatherapi.com/v1/current.json?key=4097b874e4714d4faf7112630210907")
    Call<LocationContainer> getLocData(@Query("q") String name);
}
