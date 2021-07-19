package com.jk.datapersistencedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.jk.datapersistencedemo.adapters.MenuAdapter;
import com.jk.datapersistencedemo.adapters.OnMealItemClickListener;
import com.jk.datapersistencedemo.database.Meal;
import com.jk.datapersistencedemo.databinding.ActivityFavListBinding;
import com.jk.datapersistencedemo.models.MealItem;
import com.jk.datapersistencedemo.viewmodels.MealViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavListActivity extends AppCompatActivity implements OnMealItemClickListener {
    private ActivityFavListBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<MealItem> favList;
    private MenuAdapter adapter;
    private MealViewModel mealViewModel;
    private MealItem tempMealItem;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityFavListBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.helper = new ItemTouchHelper(simpleCallback);
        this.helper.attachToRecyclerView(this.binding.rvFavList);

        this.favList = new ArrayList<>();
        this.adapter = new MenuAdapter(this, this.favList, this);
        this.binding.rvFavList.setAdapter(adapter);
        this.binding.rvFavList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvFavList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        this.mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);

        //get all teh fav meals from DB
        this.mealViewModel.getAllFavMeals().observe(this, new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> mealList) {
                if (!mealList.isEmpty()){
                    Log.e(TAG, "onChanged: meals received from DB " + mealList.toString() );
                    favList.clear();

                    for(Meal tempMeal: mealList){
                        tempMealItem = new MealItem();
                        tempMealItem.setMealID(String.valueOf(tempMeal.getMealID()));
                        tempMealItem.setMealName(tempMeal.getMealName());
                        tempMealItem.setImageThumb(tempMeal.getMealImage());

                        favList.add(tempMealItem);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    Log.e(TAG, "onChanged: empty meal list");
                }
            }
        });
    }

    @Override
    public void onMealItemClicked(MealItem mealItem) {
        Log.e(TAG, "onMealItemClicked: Update the meal name");

        Meal selectedMeal = new Meal();
        selectedMeal.setMealID(Long.parseLong(mealItem.getMealID()));
        selectedMeal.setMealName(mealItem.getMealName());
        selectedMeal.setMealImage(mealItem.getImageThumb());

        AlertDialog.Builder updateAlert = new AlertDialog.Builder(this);
        updateAlert.setTitle("Update");
        updateAlert.setMessage("Provide the updated name for the dish.");

        final EditText inputName = new EditText(this);
        inputName.setText(selectedMeal.getMealName());
        updateAlert.setView(inputName);

        updateAlert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //update the meal in DB

                selectedMeal.setMealName(inputName.getText().toString());
                mealViewModel.updateMeal(selectedMeal);
                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),
                        selectedMeal.getMealName() + " has been updated",
                        Toast.LENGTH_LONG).show();
            }
        });

        updateAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        updateAlert.show();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT){
                //delete the dish from DB
                deleteMeal(viewHolder.getAdapterPosition());
            }
        }
    };

    private void deleteMeal(int position){
        MealItem selectedMeal = favList.get(position);

        //ask for confirmation
        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(this);
        deleteAlert.setTitle("Delete");
        deleteAlert.setMessage("Are you sure you want to remove "+ selectedMeal.getMealName() + " from the fav list?");

        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete the meal in DB

                Meal mealToDelete = new Meal();
                mealToDelete.setMealName(selectedMeal.getMealName());
                mealToDelete.setMealImage(selectedMeal.getImageThumb());
                mealToDelete.setMealID(Long.parseLong(selectedMeal.getMealID()));

                mealViewModel.deleteMeal(mealToDelete);

                Toast.makeText(getApplicationContext(),
                        selectedMeal.getMealName() + " has been removed from fav list",
                        Toast.LENGTH_LONG).show();
            }
        });

        deleteAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        deleteAlert.show();
    }
}