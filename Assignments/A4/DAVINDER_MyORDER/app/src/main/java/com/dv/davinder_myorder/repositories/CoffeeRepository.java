package com.dv.davinder_myorder.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dv.davinder_myorder.database.AppDB;
import com.dv.davinder_myorder.database.Coffee;
import com.dv.davinder_myorder.database.CoffeeDAO;

import java.util.List;

public class CoffeeRepository {
    private AppDB db;
    private CoffeeDAO coffeeDAO;
    public LiveData<List<Coffee>> allCoffees;

    public CoffeeRepository(Application application){
        this.db = AppDB.getDb(application);
        this.coffeeDAO = this.db.coffeeDAO();
        this.allCoffees = this.coffeeDAO.getCoffeeDetails();
    }

    public void insertMeal(Coffee coffee){
        AppDB.databaseWriteExecutor.execute(() -> {
            coffeeDAO.insert(coffee);
        });
    }

    public LiveData<List<Coffee>> getAllCoffees() {
        return this.allCoffees;
    }

    public void updateMeal(Coffee coffee){
        AppDB.databaseWriteExecutor.execute(() -> {
            coffeeDAO.update(coffee);
        });
    }
    public void deleteMeal(Coffee coffee){
        AppDB.databaseWriteExecutor.execute(() -> {
            coffeeDAO.delete(coffee);
        });
    }
}
