package com.darpal.foodrecipefinder.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.darpal.foodrecipefinder.Model.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> mutableLiveData = new MutableLiveData<>();

    public RecipeListViewModel() { }

    public LiveData<List<Recipe>> getRecipes(){
        return mutableLiveData;
    }
}
