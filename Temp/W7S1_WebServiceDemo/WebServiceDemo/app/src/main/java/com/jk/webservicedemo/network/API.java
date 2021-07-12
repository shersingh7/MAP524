package com.jk.webservicedemo.network;

import com.jk.webservicedemo.models.CategoryContainer;

import retrofit2.Call;
import retrofit2.http.GET;

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
}
