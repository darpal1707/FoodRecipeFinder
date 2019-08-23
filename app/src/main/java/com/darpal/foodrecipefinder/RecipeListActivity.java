package com.darpal.foodrecipefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.StrictMode;
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

        button = (Button) findViewById(R.id.testButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofit();
            }
        });
    }

    private void subscribeObservers(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes!=null) {
                    for (Recipe recipe : recipes) {
                        Log.d("Recipe Titles", recipe.getTitle());
                    }
                }
            }
        });
    }
    public void testRetrofit(){
     searchRecipesApi("Chicken", 1);
    }

    private void searchRecipesApi(String query, int pageNumber){
        recipeListViewModel.searchRecipesApi(query, pageNumber);
    }
}
