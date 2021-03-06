package com.jk.webservicedemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * WebServiceDemo Created by jkp.
 */
public class Category {
    private @SerializedName("temp_c") String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
