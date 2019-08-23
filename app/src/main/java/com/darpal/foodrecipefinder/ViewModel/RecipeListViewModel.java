package com.darpal.foodrecipefinder.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.darpal.foodrecipefinder.Model.Recipe;
import com.darpal.foodrecipefinder.Repository.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository repository;
    public RecipeListViewModel() {
        repository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return repository.getRecipe();
    }

    public void searchRecipesApi(String query, int pageNumber){
        repository.searchRecipesApi(query, pageNumber);
    }
}
