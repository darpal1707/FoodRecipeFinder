package com.darpal.foodrecipefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.darpal.foodrecipefinder.Model.Recipe;
import com.darpal.foodrecipefinder.Requests.RecipeApi;
import com.darpal.foodrecipefinder.Requests.Responses.RecipeResponse;
import com.darpal.foodrecipefinder.Requests.Responses.RecipeSearchResponse;
import com.darpal.foodrecipefinder.Requests.ServiceGenerator;
import com.darpal.foodrecipefinder.Util.Constants;
import com.darpal.foodrecipefinder.ViewModel.RecipeListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//RECIPE LIST ACTIVITY ====> RecipeListActivity

public class RecipeListActivity extends BaseActivity {

    Button button;
    private RecipeListViewModel recipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        subscribeObservers();
    }

    private void subscribeObservers(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {

            }
        });
    }
    public void testRetrofit(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        /*Call<RecipeSearchResponse> searchResponseCall = recipeApi.searchRecipe(
                Constants.API_KEY,
                "Chicken",
                "2"
        );
        searchResponseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                Log.d("Response", "on server response: "+ response.toString());
                if(response.code() == 200){
                    Log.d("Response success", response.body().toString());
                    List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
                    for(Recipe recipe : recipes){
                        Log.d("recipe titles are:", recipe.getTitle() + " " + recipe.getRecipe_id());
                    }
                }
                else {
                    try {
                        Log.d("some other code", response.errorBody().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) { }


        });*/


        //******** Code for showing a particular recipe on click ********\\
        Call<RecipeResponse> responseCall = recipeApi.recipeResponse(
                Constants.API_KEY,
                "35382"
        );
        responseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.d("Response", "on server response: "+ response.toString());
                if(response.code() == 200){
                    Log.d("Response success", response.body().toString());
                    Recipe recipe = response.body().getRecipe();
                    Log.d("recipe received", recipe.toString());
                }
                else {
                    try {
                        Log.d("error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

            }
        });
    }

}
