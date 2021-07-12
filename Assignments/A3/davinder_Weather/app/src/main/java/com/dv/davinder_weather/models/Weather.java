package com.dv.davinder_weather.models;

import com.google.gson.annotations.SerializedName;

public class Weather {
    //location name
    @SerializedName("name")
    String name;

    //country
    @SerializedName("country")
    String country;

    //current temp_c
    @SerializedName("temp_c")
    String temp;


    // location -> City Name
    //@SerializedName("cloud")
    //String cloud;

    // location -> country
//    @SerializedName("cloud")
//    String cloud;

    //condition
    @SerializedName("condition")
    Condition condition;

    //feels like
    @SerializedName("feelslike_c")
    String feelsLike;

    //humidity
    @SerializedName("humidity")
    String humidity;

    //wind
    @SerializedName("wind_kph")
    String wind;

    //wind direction
    @SerializedName("wind_dir")
    String windDirection;

    //uv
    @SerializedName("uv")
    String uv;

    //visibility
    @SerializedName("vis_km")
    String visibility;







    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
