package com.jk.webservicedemo.network;

import com.jk.webservicedemo.models.CategoryContainer;
import com.jk.webservicedemo.models.RecipeContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * WebServiceDemo Created by jkp.
 */
public interface API {
    //must use https - secured
    //must end with /
    String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

//    @GET("./list.php?c=list")
    @GET("./categories.php")
    Call<CategoryContainer> retrieveCategories();

//    https://www.themealdb.com/api/json/v1/1/random.php
    @GET("./random.php")
    Call<RecipeContainer> retrieveRandomRecipe();

//    www.themealdb.com/api/json/v1/1/filter.php?i=chicken_breast
    @GET("./filter.php")
    Call<RecipeContainer> retrieveMealsByCategory(@Query("i") String categoryName);

//    to call this function -- retrieveMealsByCategory("Pizza")
//    www.themealdb.com/api/json/v1/1/filter.php?i=Pizza

//    www.themealdb.com/api/json/v1/1/lookup.php?i=52772&a=Canadian
    @GET("./lookup.php")
    Call<RecipeContainer> retrieveMealsByCategoryID(@Query("i") int id, @Query("a") String categoryName);
    //    to call this function in MainActivity-- retrieveMealsByCategoryID(101,"Pasta")
//    www.themealdb.com/api/json/v1/1/filter.php?i=101&a=Pasta

//    @GET("./lookup.php?i={id}")
    @GET("https://www.themealdb.com/api/json/v1/1/lookup.php?i={id}")
    Call<RecipeContainer> retrieveMealsByID(@Path("id") int ID);
}
