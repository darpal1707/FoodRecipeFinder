package com.darpal.foodrecipefinder.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.darpal.foodrecipefinder.Model.Recipe;
import com.darpal.foodrecipefinder.Requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private static RecipeApiClient mRecipeApiClient;

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private RecipeRepository() {
        mRecipeApiClient = RecipeApiClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipe() {
        return mRecipeApiClient.getRecipe();
    }

    public void searchRecipesApi(String query, int pageNumber){
        if(pageNumber == 0){
            pageNumber = 1;
        }
        mRecipeApiClient.searchRecipeApi(query, pageNumber);
    }
}
