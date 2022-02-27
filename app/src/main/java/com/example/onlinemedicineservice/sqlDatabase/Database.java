package com.example.onlinemedicineservice.sqlDatabase;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {SQLProductModel.class, SQLOrderModel.class} , version = 10, exportSchema = false)
public abstract class Database extends RoomDatabase{

    private static volatile Database productDatabaseInstance;

    public abstract DatabaseDAO productDAO();

    private static final int NUMBER_OF_THREADS = 8;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static Database getInstance(Context context){

        if(productDatabaseInstance == null){

            productDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), Database.class,
                    "local_database").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return productDatabaseInstance;
    }

}
