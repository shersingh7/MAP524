package com.dv.test.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CountryDB.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase{

    private static volatile AppDB db;
    private static final Integer NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract CountryDAO countryDAO();

    public static AppDB getDb(final Context context){
        if(db == null){
            db = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "db_country")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

}
