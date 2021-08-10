package com.dv.test.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dv.test.database.CountryDB;
import com.dv.test.repositores.CountryRepository;

import java.util.List;

public class CountryViewModel extends AndroidViewModel {

    private CountryRepository countryRepository;
    private LiveData<List<CountryDB>> allCountries;

    public CountryViewModel(Application application) {
        super(application);
        this.countryRepository = new CountryRepository(application);
        this.allCountries = this.countryRepository.allCountries;
    }

    public LiveData<List<CountryDB>> getAllCountries() {
        return allCountries;
    }

    public void insertCountry(CountryDB newCountry){
        this.countryRepository.insertCountry(newCountry);
    }

    public void deleteCountry(CountryDB country){
        this.countryRepository.deleteCountry(country);
    }

}
