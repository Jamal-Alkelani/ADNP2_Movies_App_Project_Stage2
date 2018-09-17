package com.example.gamal.adnp2_movies_app_project_stage1.Utilities;


import android.util.Pair;
import android.widget.Toast;

import com.example.gamal.adnp2_movies_app_project_stage1.Models.Movie;
import com.example.gamal.adnp2_movies_app_project_stage1.Models.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movies_JSONHandler {
    private String json;

    public Movies_JSONHandler(String json) {
        this.json = json;
    }

    public List<Movie> extractFromJSON() {
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                Movie movie = new Movie();
                JSONObject movieObj = results.getJSONObject(i);
                movie.setVoteAvg(movieObj.get("vote_average").toString());
                movie.setTitle(movieObj.get("title").toString());
                movie.setMoviePoster("https://image.tmdb.org/t/p/w500" + movieObj.get("poster_path").toString());
                movie.setCategory(movieObj.get("adult").toString());
                movie.setDesc(movieObj.get("overview").toString());
                movie.setReleaseDate(movieObj.get("release_date").toString());
                movie.setMovieID(movieObj.get("id").toString());
                movies.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public Pair<String,String>[] getMovieURL() {
        String movieURL = "";
        String movieName = "";
        Pair<String,String> urls[]=null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            urls=new Pair[results.length()];
            if (results != null) {
                for (int i=0;i<results.length();i++){
                    JSONObject movieObj = results.getJSONObject(i);
                    movieURL = "https://www.youtube.com/watch?v="+movieObj.get("key").toString();
                    movieName = movieObj.get("name").toString();
                    urls[i]=new Pair<>(movieURL,movieName);
                }

            } else {
                Toast.makeText(null, "No Trailers Found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return urls;
    }

    public List<Review> getMovieReviews() {
        String reviewAuthor = "";
        String reviewcontent = "";
        String reviewURL = "";

        List<Review> movieReviews = new ArrayList<Review>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieObj = results.getJSONObject(i);
                    reviewAuthor = movieObj.get("author").toString();
                    reviewcontent = movieObj.get("content").toString();
                    reviewURL = movieObj.get("url").toString();
                    Review review = new Review(reviewAuthor, reviewcontent, reviewURL);
                    movieReviews.add(review);
                }
            } else {
                Toast.makeText(null, "No Reviews Found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieReviews;
    }
}
