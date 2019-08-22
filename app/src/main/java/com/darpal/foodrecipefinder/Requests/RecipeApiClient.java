package com.darpal.foodrecipefinder.Requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.darpal.foodrecipefinder.AppExecutors;
import com.darpal.foodrecipefinder.Model.Recipe;
import com.darpal.foodrecipefinder.Requests.Responses.RecipeSearchResponse;
import com.darpal.foodrecipefinder.Util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.darpal.foodrecipefinder.Util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {

    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private retrieveRecipesRunnable recipesRunnable;

    private RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
    }

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }
    public LiveData<List<Recipe>> getRecipe() {
        return mRecipes;
    }

    public void searchRecipeApi(String query, int pageno) {
        if(recipesRunnable != null){
            recipesRunnable = null;
        }
        recipesRunnable = new retrieveRecipesRunnable(query, pageno);
        final Future handler = AppExecutors.getInstance().networkIO().submit(recipesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //stop the request
                //let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class retrieveRecipesRunnable implements Runnable{

        private String query;
        private int pageno;
        Boolean cancelRequest;

        public retrieveRecipesRunnable(String query, int pageno) {
            this.query = query;
            this.pageno = pageno;
            cancelRequest = false;
        }
        @Override
        public void run() {
            try {
                Response response = getRecipes(query,pageno).execute();
                if (cancelRequest = true){
                    return;
                }
                if(response.code() == 200){
                    List<Recipe> recipeList = new ArrayList<>(((RecipeSearchResponse)response.body()).getRecipes());
                    if(pageno ==1){
                        mRecipes.postValue(recipeList);
                    }
                    else {
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(recipeList);
                        mRecipes.postValue(currentRecipes);
                    }
                }
                else {
                    String error = response.errorBody().string();
                    Log.e("Error code", error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageno){
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    Constants.API_KEY,
                    query,
                    String.valueOf(pageno)
            );
        }

        private void cancelRequest(){
            Log.d("Request canceled", "Request cancelled");
            cancelRequest = true;
        }
    }
}
