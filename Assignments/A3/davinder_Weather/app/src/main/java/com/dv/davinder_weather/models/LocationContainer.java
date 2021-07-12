package com.dv.davinder_weather.models;

import com.google.gson.annotations.SerializedName;

public class LocationContainer {

    @SerializedName("location")
    private Location location;

    public Location getLocation() {return location;}

    public void setWeather(Location location) {this.location = location;}

}
