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

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> recipes;
    private OnRecipeListener onRecipeListener;

    public RecipeAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
        return new RecipeViewHolder(view, onRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(recipes.get(position).getImage_url())
                .into(((RecipeViewHolder)holder).imageView);

        ((RecipeViewHolder)holder).title.setText(recipes.get(position).getTitle());
        ((RecipeViewHolder)holder).publisher.setText(recipes.get(position).getPublisher());
        ((RecipeViewHolder)holder).socialScore.setText(String.valueOf(Math.round(recipes.get(position).getSocial_rank())));

    }

    @Override
    public int getItemCount() {
        if(recipes != null) {
            return recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipe){
        recipes = recipe;
        notifyDataSetChanged();
    }
}
