package com.example.gamal.adnp2_movies_app_project_stage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gamal.adnp2_movies_app_project_stage1.Models.Movie;
import com.example.gamal.adnp2_movies_app_project_stage1.database.AppDatabase;
import com.example.gamal.adnp2_movies_app_project_stage1.database.AppExecutors;

import java.util.List;

public class Favorite extends AppCompatActivity {
    private AppDatabase mDB;
    private ListView lv;
    String[] favMovieTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mDB = AppDatabase.getInstance(this);
        lv = findViewById(R.id.favMovies);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Movie> favMovies = fetchFavMovies();
                favMovieTitles = new String[favMovies.size()];
                for (int i = 0; i < favMovies.size(); i++) {
                    favMovieTitles[i] = favMovies.get(i).getTitle().toString();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (favMovieTitles.length > 0) {
                            findViewById(R.id.noFavMoviesErrorMsg).setVisibility(View.GONE);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,
                                    android.R.id.text1, favMovieTitles);
                            lv.setAdapter(adapter);
                        } else {
                            findViewById(R.id.noFavMoviesErrorMsg).setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    public List<Movie> fetchFavMovies() {
        List<Movie> movies = mDB.movieDao().loadAllTasks();
        return movies;
    }
}
