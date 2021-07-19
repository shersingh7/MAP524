package com.jk.datapersistencedemo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * DataPersistenceDemo Created by jkp.
 */
@Dao
public interface MealDAO {
    @Query("SELECT * FROM tbl_fav_meals ORDER BY meal_name ASC")
    LiveData<List<Meal>> getAllFavMeals();

    @Query("SELECT * FROM tbl_fav_meals WHERE meal_id = :id")
    Meal getMealById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);
}
