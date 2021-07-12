package com.dv.davinder_weather.models;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("name")
    String city;

    @SerializedName("country")
    String country;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
