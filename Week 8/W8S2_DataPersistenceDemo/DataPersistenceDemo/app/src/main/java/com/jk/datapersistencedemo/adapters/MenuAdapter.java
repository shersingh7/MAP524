package com.jk.datapersistencedemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.datapersistencedemo.databinding.MenuItemRowBinding;
import com.jk.datapersistencedemo.models.MealItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * DataPersistenceDemo Created by jkp.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final Context mContext;
    private final ArrayList<MealItem> mealItemList;
    private final OnMealItemClickListener itemClickListener;
    private static final String TAG = "MenuAdapter";

    public MenuAdapter(Context mContext, ArrayList<MealItem> mealItemList, OnMealItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mealItemList = mealItemList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return this.mealItemList.size();
    }

    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuViewHolder(MenuItemRowBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuViewHolder holder, int position) {
        final MealItem currentMealItem = mealItemList.get(position);
        holder.bind(mContext, currentMealItem, itemClickListener);
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        MenuItemRowBinding binding;

        public MenuViewHolder(MenuItemRowBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bind(Context context, final MealItem currentMeal, final OnMealItemClickListener clickListener) {

            binding.tvTitle.setText(currentMeal.getMealName());
            Picasso.get()
                    .load(currentMeal.getImageThumb())
                    .placeholder(ContextCompat.getDrawable(context, android.R.drawable.ic_media_next))
                    .into(binding.imgFood);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    clickListener.onMealItemClicked(currentMeal);


                }
            });
        }
    }
}
