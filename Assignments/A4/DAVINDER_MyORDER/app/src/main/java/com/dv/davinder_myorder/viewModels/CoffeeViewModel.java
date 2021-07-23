package com.dv.davinder_myorder.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dv.davinder_myorder.database.Coffee;
import com.dv.davinder_myorder.repositories.CoffeeRepository;

import java.util.List;

public class CoffeeViewModel extends AndroidViewModel {

    private CoffeeRepository coffeeRepository;
    private LiveData<List<Coffee>> allCoffees;

    public CoffeeViewModel(Application application) {
        super(application);
        this.coffeeRepository = new CoffeeRepository(application);
        this.allCoffees = this.coffeeRepository.allCoffees;
    }

    public LiveData<List<Coffee>> getAllCoffees() {
        return allCoffees;
    }

    public void insertCoffee(Coffee newCoffee){
        this.coffeeRepository.insertMeal(newCoffee);
    }

    public void updateCoffee(Coffee coffee){
        this.coffeeRepository.updateMeal(coffee);
    }

    public void deleteCoffee(Coffee coffee){
        this.coffeeRepository.deleteMeal(coffee);
    }

}
