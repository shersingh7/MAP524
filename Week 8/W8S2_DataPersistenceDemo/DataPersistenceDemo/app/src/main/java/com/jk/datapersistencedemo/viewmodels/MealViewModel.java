package com.jk.datapersistencedemo.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jk.datapersistencedemo.database.Meal;
import com.jk.datapersistencedemo.repositories.MealRepository;

import java.util.List;

/**
 * DataPersistenceDemo Created by jkp.
 */
public class MealViewModel extends AndroidViewModel {
    private MealRepository mealRepo;
    private LiveData<List<Meal>> allFavMeals;

    public MealViewModel(Application application) {
        super(application);
        this.mealRepo = new MealRepository(application);
        this.allFavMeals = this.mealRepo.allMeals;
    }

    public LiveData<List<Meal>> getAllFavMeals() {
        return allFavMeals;
    }

    public Meal getMealById(long id){
        return this.mealRepo.getMealById(id);
    }

    public void insertMeal(Meal newMeal){
        this.mealRepo.insertMeal(newMeal);
    }

    public void updateMeal(Meal meal){
        this.mealRepo.updateMeal(meal);
    }

    public void deleteMeal(Meal meal){
        this.mealRepo.deleteMeal(meal);
    }
}
