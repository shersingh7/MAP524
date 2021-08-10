package com.dv.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dv.test.adapters.CountryAdapterMain;
import com.dv.test.adapters.OnCountryListClickListener;
import com.dv.test.database.CountryDB;
import com.dv.test.databinding.ActivityMainBinding;
import com.dv.test.models.CountryList;
import com.dv.test.network.RetrofitClient;
import com.dv.test.viewModel.CountryViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnCountryListClickListener {

    private final String TAG = this.getClass().getCanonicalName();
    private CountryAdapterMain adapter;
    private CountryViewModel countryViewModel;
    private ArrayList<CountryList> list;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);

        this.list = new ArrayList<>();

        this.adapter = new CountryAdapterMain(this, this.list, this);

        this.binding.rvMain.setAdapter(adapter);
        this.binding.rvMain.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Log.d(TAG, "onCreate: list" + list);

        this.getCountriesFromWeb();

    }

    private void getCountriesFromWeb() {
        Call<JsonArray> CountryContainerCall = RetrofitClient.getInstance().getApi().Countrydata();

        try {
            Log.d(TAG, "onCreate: try block " + list);
            CountryContainerCall.enqueue(new Callback<JsonArray>() {
                @Override
                public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                    if (response.code() == 200 && response.body() != null) {
                        list.clear();

                        JsonArray s = response.body().getAsJsonArray();

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<CountryList>>(){}.getType();

                        list.addAll(gson.fromJson(s, type));

                        for(int i = 0; i < list.size(); i++)

                        adapter.notifyDataSetChanged();


                        Log.d(TAG, "onResponse: Countries: " + list);



                    }else {
                        Log.e(TAG, "onResponse: No response received");
                    }
                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    t.printStackTrace();
                    Log.e(TAG, "Error while fetching data -- " + t.getLocalizedMessage());
                }
            });

        }catch (Exception e){
            Log.e(TAG, "onFailure: Error while fetching recipe" + e);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_order:{
                this.showFav();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFav(){
        Intent favListIntent = new Intent(this, FavouriteActivity.class);
        startActivity(favListIntent);
    }

    @Override
    public void onCountryListClicked(CountryList countryList) {
        Log.d(TAG, "CountryListClicked: Add to Favourite: ");

        CountryDB selectedCountry = new CountryDB();
        selectedCountry.setCountryName(countryList.getName());
        selectedCountry.setCountryFlag(countryList.getFlag());
        selectedCountry.setCountryCode(countryList.getCode());
        selectedCountry.setCountryCapital(countryList.getCapital());

        this.countryViewModel.insertCountry(selectedCountry);
        Toast.makeText(getApplicationContext(), selectedCountry.getCountryName() + " has been added to favourites", Toast.LENGTH_LONG).show();

    }
}