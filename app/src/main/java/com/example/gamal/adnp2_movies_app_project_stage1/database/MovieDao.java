package com.example.gamal.adnp2_movies_app_project_stage1.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.gamal.adnp2_movies_app_project_stage1.Models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Movie> loadAllTasks();

    @Insert
    void insertTask(Movie taskEntry);

    @Update(onConflict= OnConflictStrategy.REPLACE)
    void updateTask(Movie taskEntry);

    @Delete
    void deleteTask(Movie taskEntry);

    @Query("SELECT * FROM movies WHERE movieID= :id")
    Movie loadTaskById(int id);
}
