package com.darpal.foodrecipefinder.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.darpal.foodrecipefinder.Model.Recipe;
import com.darpal.foodrecipefinder.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<Recipe> recipes;
    private OnRecipeListener onRecipeListener;

    public RecipeAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewObej = null;
        switch (viewType) {
            case RECIPE_TYPE: {
                viewObej = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(viewObej, onRecipeListener);
            }

            case LOADING_TYPE: {
                viewObej = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading, parent, false);
                return new LoadingViewHolder(viewObej);
            }

            default: {
                viewObej = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(viewObej, onRecipeListener);
            }

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipes.get(position).getImage_url())
                    .into(((RecipeViewHolder) holder).imageView);

            ((RecipeViewHolder) holder).title.setText(recipes.get(position).getTitle());
            ((RecipeViewHolder) holder).publisher.setText(recipes.get(position).getPublisher());
            ((RecipeViewHolder) holder).socialScore.setText(String.valueOf(Math.round(recipes.get(position).getSocial_rank())));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (recipes.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            recipes = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (recipes != null) {
            if (recipes.size() > 0) {
                if (recipes.get(recipes.size() - 1).getTitle().equals("LOADING...")) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int getItemCount() {
        if (recipes != null) {
            return recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipe) {
        recipes = recipe;
        notifyDataSetChanged();
    }
}
