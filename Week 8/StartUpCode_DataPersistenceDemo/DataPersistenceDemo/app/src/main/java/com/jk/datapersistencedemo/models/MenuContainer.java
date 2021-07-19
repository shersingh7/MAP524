package com.jk.datapersistencedemo.models;

import java.util.ArrayList;

/**
 * DataPersistenceDemo Created by jkp.
 */
public class MenuContainer {
    private ArrayList<MealItem> meals;

    public ArrayList<MealItem> getMeals() {
        return meals;
    }

    @Override
    public String toString() {
        return "MenuContainer{" +
                "meals=" + meals +
                '}';
    }
}
