package com.jk.datapersistencedemo.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jk.datapersistencedemo.database.AppDB;
import com.jk.datapersistencedemo.database.Meal;
import com.jk.datapersistencedemo.database.MealDAO;

import java.util.List;

/**
 * DataPersistenceDemo Created by jkp.
 */
public class MealRepository {
    private AppDB db;
    private MealDAO mealDAO;
    public LiveData<List<Meal>> allMeals;

    public MealRepository(Application application){
        this.db = AppDB.getDatabase(application);
        this.mealDAO = this.db.mealDAO();
        this.allMeals = this.mealDAO.getAllFavMeals();
    }

    public void insertMeal(Meal newMeal){
        AppDB.databaseWriteExecutor.execute(() -> {
            mealDAO.insert(newMeal);
        });
    }

    public LiveData<List<Meal>> getAllMeals() {
        return this.allMeals;
    }

    public Meal getMealById(long id){
        return this.mealDAO.getMealById(id);
    }

    public void updateMeal(Meal meal){
        this.mealDAO.update(meal);
    }

    public void deleteMeal(Meal meal){
        this.mealDAO.delete(meal);
    }
}
