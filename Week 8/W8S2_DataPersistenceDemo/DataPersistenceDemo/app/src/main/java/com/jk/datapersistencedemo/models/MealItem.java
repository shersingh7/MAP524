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

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public void setMealID(String mealID) {
        this.mealID = mealID;
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
