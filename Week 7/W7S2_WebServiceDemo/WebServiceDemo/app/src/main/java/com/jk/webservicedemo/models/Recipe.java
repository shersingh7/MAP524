package com.jk.webservicedemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * WebServiceDemo Created by jkp.
 */
public class Recipe {
    private @SerializedName("strMeal") String recipeName;
    private @SerializedName("strArea") String regionName;
    private @SerializedName("strMealThumb") String imageThumb;

    public String getRecipeName() {
        return recipeName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeName='" + recipeName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", imageThumb='" + imageThumb + '\'' +
                '}';
    }
}
