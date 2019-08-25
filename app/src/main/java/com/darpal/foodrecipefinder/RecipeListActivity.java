package com.darpal.foodrecipefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.darpal.foodrecipefinder.Adapters.OnRecipeListener;
import com.darpal.foodrecipefinder.Adapters.RecipeAdapter;
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

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    RecyclerView recyclerView;
    private RecipeListViewModel recipeListViewModel;
    RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recyclerView = (RecyclerView) findViewById(R.id.recipe_recycler);
        initRecyclerView();
        recipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        subscribeObservers();
        initSearchView();
    }

    private void subscribeObservers(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes!=null) {
                    for (Recipe recipe : recipes) {
                        Log.d("Recipe Titles", recipe.getTitle());
                    }
                    recipeAdapter.setRecipes(recipes);
                }

            }
        });
    }

    private void initRecyclerView(){
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void initSearchView(){
        final androidx.appcompat.widget.SearchView searchView =  findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeAdapter.displayLoading();
                recipeListViewModel.searchRecipesApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}
