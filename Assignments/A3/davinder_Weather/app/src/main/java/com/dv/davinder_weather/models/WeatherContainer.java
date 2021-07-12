package com.dv.davinder_weather.models;

import com.google.gson.annotations.SerializedName;

public class WeatherContainer {


    @SerializedName("current")
    private Weather weather;

    public Weather getWeather() {return weather;}

    public void setWeather(Weather weather) {this.weather = weather;}

}
