package com.example.gamal.adnp2_movies_app_project_stage1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.example.gamal.adnp2_movies_app_project_stage1.Models.Movie;

@Database(entities ={Movie.class},version = 1,exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="FavMovies";
    private static final String LOG_TAG= AppDatabase.class.getSimpleName();
    private static final Object LOCK=new Object();
    private static  AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance==null){
            synchronized (LOCK){
                Log.d(LOG_TAG,"Creating New Database Instance");
                sInstance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG,"Getting The Database Instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();
}
