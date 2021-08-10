package com.dv.test.repositores;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dv.test.database.AppDB;
import com.dv.test.database.CountryDAO;
import com.dv.test.database.CountryDB;

import java.util.List;

public class CountryRepository {
    private AppDB db;
    private CountryDAO countryDAO;

    public LiveData<List<CountryDB>> allCountries;

    public CountryRepository(Application application){
        this.db = AppDB.getDb(application);
        this.countryDAO = this.db.countryDAO();
        this.allCountries = this.countryDAO.getCountryDetails();
    }

    public void insertCountry(CountryDB country){
        AppDB.databaseWriteExecutor.execute(() -> {
            countryDAO.insert(country);
        });
    }

    public LiveData<List<CountryDB>> getAllCountries() {
        return this.allCountries;
    }

    public void deleteCountry(CountryDB country){
        AppDB.databaseWriteExecutor.execute(() -> {
            countryDAO.delete(country);
        });
    }

}
