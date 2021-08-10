package com.dv.test.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;
import androidx.room.Query;

import java.util.List;


@Dao
public interface CountryDAO {

    @Query("SELECT * FROM tbl_country")
    LiveData<List<CountryDB>> getCountryDetails();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CountryDB coffee);

    @Update
    void update(CountryDB coffee);

    @Delete
    void delete(CountryDB coffee);

}
