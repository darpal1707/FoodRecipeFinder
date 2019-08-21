package com.darpal.foodrecipefinder.Requests;

import com.darpal.foodrecipefinder.Requests.Responses.RecipeResponse;
import com.darpal.foodrecipefinder.Requests.Responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    //Search Recipe
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("key") String key,
            @Query("q") String query,
            @Query("page") String page
    );

    @GET("api/get")
    Call<RecipeResponse> recipeResponse(
            @Query("key") String key,
            @Query("rId") String recipe_id
    );
}
