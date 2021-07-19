package com.jk.datapersistencedemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * DataPersistenceDemo Created by jkp.
 */
public class MealItem {
    private @SerializedName("strMeal") String mealName;
    private @SerializedName("strMealThumb") String imageThumb;
    private @SerializedName("idMeal") String mealID;

    public String getMealName() {
        return mealName;
    }

    public String getImageThumb() {
        return imageThumb;
    }

    public String getMealID() {
        return mealID;
    }

    @Override
    public String toString() {
        return "MealItem{" +
                "recipeName='" + mealName + '\'' +
                ", imageThumb='" + imageThumb + '\'' +
                ", mealID='" + mealID + '\'' +
                '}';
    }
}
