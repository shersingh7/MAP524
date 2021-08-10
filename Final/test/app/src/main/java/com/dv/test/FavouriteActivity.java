package com.dv.test;


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dv.test.adapters.CountryAdapterFav;
import com.dv.test.adapters.OnCountryListClickListener;
import com.dv.test.database.CountryDB;
import com.dv.test.databinding.ActivityFavouriteBinding;
import com.dv.test.models.CountryList;
import com.dv.test.viewModel.CountryViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity implements OnCountryListClickListener{


    ActivityFavouriteBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<CountryList> list;
    private CountryViewModel countryViewModel;
    private CountryList countryList;
    private CountryAdapterFav adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.list = new ArrayList<>();
        this.adapter = new CountryAdapterFav(this, this.list, this);
        this.binding.rvFavourite.setAdapter(adapter);
        this.binding.rvFavourite.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvFavourite.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        this.countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);

        this.countryViewModel.getAllCountries().observe(this, new Observer<List<CountryDB>>() {
            @Override
            public void onChanged(List<CountryDB> countries) {
                if(!countries.isEmpty()){
                    Log.d(TAG, "onChanged: countries received: " + countries.toString());

                    list.clear();

                    for(CountryDB temp: countries){
                        countryList = new CountryList();
                        countryList.setName(temp.getCountryName());
                        countryList.setFlag(temp.getCountryFlag());
                        countryList.setCapital(temp.getCountryCapital());
                        countryList.setCode(temp.getCountryCode());


                        list.add(countryList);
                    }
                    adapter.notifyDataSetChanged();

                }else {
                    Log.d(TAG, "onChanged: No countries received");
                }
            }
        });

    }

    @Override
    public void onCountryListClicked(CountryList selectedCountry) {
        Log.e(TAG, "onMealItemClicked: Meal selected");

        CountryDB toBeDeleted = new CountryDB();
        toBeDeleted.setCountryName(selectedCountry.getName());
        toBeDeleted.setCountryCapital(selectedCountry.getCapital());
        toBeDeleted.setCountryFlag(selectedCountry.getFlag());
        toBeDeleted.setCountryCode(selectedCountry.getCode());

        countryViewModel.deleteCountry(toBeDeleted);

        Toast.makeText(getApplicationContext(), selectedCountry.getName() + " has been deleted", Toast.LENGTH_LONG).show();

        adapter.notifyDataSetChanged();

    }
}