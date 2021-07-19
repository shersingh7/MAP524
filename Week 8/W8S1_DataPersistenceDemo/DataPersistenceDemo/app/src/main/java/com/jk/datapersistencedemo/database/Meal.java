package com.jk.datapersistencedemo.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * DataPersistenceDemo Created by jkp.
 */
@Entity(tableName = "tbl_fav_meals")
public class Meal {

//    @PrimaryKey(autoGenerate = true)
    @PrimaryKey
    @ColumnInfo(name = "meal_id")
    private long mealID;

    @NonNull
    @ColumnInfo(name = "meal_name")
    private String mealName;

    @ColumnInfo(name = "meal_image_url")
    private String mealImage;

    public Meal() {
    }

    public Meal(long mealID, String mealName, String mealImage) {
        this.mealID = mealID;
        this.mealName = mealName;
        this.mealImage = mealImage;
    }

    public long getMealID() {
        return mealID;
    }

    public void setMealID(long mealID) {
        this.mealID = mealID;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealImage() {
        return mealImage;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealID=" + mealID +
                ", mealName='" + mealName + '\'' +
                ", mealImage='" + mealImage + '\'' +
                '}';
    }
}
