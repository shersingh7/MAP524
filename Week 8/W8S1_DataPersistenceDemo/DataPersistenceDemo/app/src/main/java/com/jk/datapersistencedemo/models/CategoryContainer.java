package com.jk.datapersistencedemo.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * WebServiceDemo Created by jkp.
 */
public class CategoryContainer {
    private @SerializedName("categories") ArrayList<Category> categoryList;

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    @Override
    public String toString() {
        return "CategoryContainer{" +
                "categoryList=" + categoryList +
                '}';
    }
}
