package com.dv.davinder_myorder.database;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CoffeeDAO {

    @Query("SELECT * FROM tbl_coffee")
    LiveData<List<Coffee>> getCoffeeDetails();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Coffee coffee);

    @Update
    void update(Coffee coffee);

    @Delete
    void delete(Coffee coffee);


}
