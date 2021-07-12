package com.dv.davinder_weather.models;


import com.google.gson.annotations.SerializedName;

public class Condition {

    @SerializedName("text")
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
