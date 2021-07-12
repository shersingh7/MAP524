package com.jk.webservicedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.jk.webservicedemo.databinding.ActivityMainBinding;
import com.jk.webservicedemo.models.CategoryContainer;
import com.jk.webservicedemo.models.Recipe;
import com.jk.webservicedemo.models.RecipeContainer;
import com.jk.webservicedemo.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityMainBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<String> categoryList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.categoryList = new ArrayList<>();
        this.getCategoryList();

        // Create an ArrayAdapter using the string array and a default spinner layout
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.categoryList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.binding.spnCategories.setAdapter(adapter);
        this.binding.spnCategories.setOnItemSelectedListener(this);

        this.binding.btnGetReceipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomRecipe();
            }
        });
    }

    private void getCategoryList(){
        Log.d(TAG, "getCategoryList: Getting category list");
        Call<CategoryContainer> categoryCall = RetrofitClient.getInstance().getApi().retrieveCategories();

        try{
            categoryCall.enqueue(new Callback<CategoryContainer>() {
                @Override
                public void onResponse(Call<CategoryContainer> call, Response<CategoryContainer> response) {
                    if (response.code() == 200){
                        Log.d(TAG, "onResponse: Response successful");

                        CategoryContainer mainResponse = response.body();
                        Log.d(TAG, "onResponse: " + mainResponse.getCategoryList().size() + " objects received.");

                        if (!mainResponse.getCategoryList().isEmpty()){
                            categoryList.clear();
                            for (int i=0; i<mainResponse.getCategoryList().size(); i++){
                                categoryList.add(mainResponse.getCategoryList().get(i).getCategoryName());
                                Log.d(TAG, "onResponse: Category " + i + ": " + mainResponse.getCategoryList().get(i).getCategoryName());
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        Log.e(TAG, "onResponse: Response unsuccessful");
                    }

                    call.cancel();
                }

                @Override
                public void onFailure(Call<CategoryContainer> call, Throwable t) {
                    Log.e(TAG, "onFailure: Call Failed " + t.getLocalizedMessage() );
                }
            });
        }catch (Exception ex){
            Log.e(TAG, "getCategoryList: Exception occurred while fetching category list" + ex.getLocalizedMessage() );
        }
    }

    private void getRandomRecipe(){
        Log.d(TAG, "getRandomRecipe: Getting Random Recipe");
        
        Call<RecipeContainer> recipeContainerCall = RetrofitClient.getInstance().getApi().retrieveRandomRecipe();
        try{
            recipeContainerCall.enqueue(new Callback<RecipeContainer>() {
                @Override
                public void onResponse(Call<RecipeContainer> call, Response<RecipeContainer> response) {
                    if (response.code() == 200){
                        RecipeContainer recipeContainer = response.body();
                        Log.d(TAG, "onResponse: Random Recipe Recieved " + recipeContainer.toString());
                        
                        if(recipeContainer.getMeals().isEmpty()){
                            Log.e(TAG, "onResponse: The recipe is empty");
                        }else{
                            Recipe currentRecipe = recipeContainer.getMeals().get(0);

                            binding.tvReceipeName.setText(currentRecipe.getRecipeName());
                            binding.tvRegionName.setText(currentRecipe.getRegionName());

                            //to get an image
//                            Glide.with(getApplicationContext())
//                                    .load(currentRecipe.getImageThumb())
//                                    .placeholder(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.ic_media_next))
//                                    .into(binding.imgReceipeThumb);

                            Picasso.with(getApplicationContext())
                                    .load(currentRecipe.getImageThumb())
                                    .placeholder(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.ic_media_next))
                                    .into(binding.imgReceipeThumb);
                        }

                    }else{
                        Log.e(TAG, "onResponse: Unsuccessful response from provider");
                    }

                    recipeContainerCall.cancel();
                }

                @Override
                public void onFailure(Call<RecipeContainer> call, Throwable t) {
                    Log.e(TAG, "onFailure: Error while fetching recipe" + t.getLocalizedMessage() );
                }
            });
        }catch(Exception e){
            Log.e(TAG, "getRandomRecipe: Exception occurred while fetching random recipe" + e.getLocalizedMessage() );
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemSelected: Selected Category of meal : " + categoryList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "onNothingSelected: Nothing selected from spinner");
    }
}

//https://www.themealdb.com/api.php
//https://www.themealdb.com/api/json/v1/1/list.php?c=list
//https://www.themealdb.com/api/json/v1/1/random.php
//https://www.themealdb.com/api/json/v1/1/filter.php?a=Canadian