package com.jk.datapersistencedemo.models;

import java.util.ArrayList;

/**
 * WebServiceDemo Created by jkp.
 */
public class RecipeContainer {
    private ArrayList<Recipe> meals;

    public ArrayList<Recipe> getMeals() {
        return meals;
    }

    @Override
    public String toString() {
        return "RecipeContainer{" +
                "meals=" + meals +
                '}';
    }
}
