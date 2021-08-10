package com.jk.datapersistencedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jk.datapersistencedemo.adapters.MenuAdapter;
import com.jk.datapersistencedemo.adapters.OnMealItemClickListener;
import com.jk.datapersistencedemo.database.Meal;
import com.jk.datapersistencedemo.databinding.ActivityMainBinding;
import com.jk.datapersistencedemo.databinding.ActivityMenuBinding;
import com.jk.datapersistencedemo.models.MealItem;
import com.jk.datapersistencedemo.models.MenuContainer;
import com.jk.datapersistencedemo.network.RetrofitClient;
import com.jk.datapersistencedemo.viewmodels.MealViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity implements OnMealItemClickListener {
    ActivityMenuBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<MealItem> menuItemList;
    String selectedCategory = "";
    private MenuAdapter adapter;
    private MealViewModel mealViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);

        this.menuItemList = new ArrayList<>();
        adapter = new MenuAdapter(this, menuItemList, this);
        this.binding.rvMenuList.setAdapter(adapter);
        this.binding.rvMenuList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvMenuList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Log.d(TAG, "onCreate: rvMenuList: " + menuItemList);

        this.selectedCategory = this.getIntent().getStringExtra("EXTRA_CATEGORY");
        if (!this.selectedCategory.isEmpty()){
            this.getMenuItems();
        }
    }

    private void getMenuItems(){
        Call<MenuContainer> call = RetrofitClient.getInstance().getApi().retrieveMealsByCategory(this.selectedCategory);

        try{
            Log.d(TAG, "onResponse: menuItemList: before response" + menuItemList);

            call.enqueue(new Callback<MenuContainer>() {
                @Override
                public void onResponse(Call<MenuContainer> call, Response<MenuContainer> response) {
                    if (response.code() == 200 && response.body() != null) {
                        MenuContainer main_response = (MenuContainer) response.body();

                        Log.e(TAG, main_response.getMeals().size()+ " objects received.");
                        menuItemList.clear();
                        menuItemList.addAll(main_response.getMeals());
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "onResponse: menuItemList: " + menuItemList);
                    }else{
                        Log.e(TAG, "onResponse: No response received");
                    }

                    call.cancel();
                }

                @Override
                public void onFailure(Call<MenuContainer> call, Throwable t) {
                    Log.e(TAG, "Error while fetching data -- " + t.getLocalizedMessage());
                }
            });
        }catch (Exception ex){
            Log.e(TAG, "Exception occurred : " + ex.getLocalizedMessage());
        }
    }

    @Override
    public void onMealItemClicked(MealItem mealItem) {
        Log.e(TAG, "onMealItemClicked: Meal selected");

        Meal newMeal = new Meal();
        newMeal.setMealID(Long.parseLong(mealItem.getMealID()));
        newMeal.setMealName(mealItem.getMealName());
        newMeal.setMealImage(mealItem.getImageThumb());

        this.mealViewModel.insertMeal(newMeal);
        Log.d(TAG, "onMealItemClicked: Item inserted" + newMeal.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_show_fav:{
                Log.e(TAG, "onOptionsItemSelected: Open Fav List Activity");
                Intent favIntent = new Intent(this, FavListActivity.class);
                startActivity(favIntent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}