package com.example.gamal.adnp2_movies_app_project_stage1.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkHandler {
    private final String SEARCH_MOVIES_URL = "https://api.themoviedb.org/3/search/movie";
    private final String DISCOVER_MOVIES_URL = "https://api.themoviedb.org/3/discover/movie";
    private final String REVIEWS_MOVIES_URL = "https://api.themoviedb.org/3/movie/";
    private final String TRAILER_MOVIES_URL = "https://api.themoviedb.org/3/movie/";
    private final String QUERY_PARAM = "query";
    private final String API_KEY_PARAM = "api_key";
    //TODO insert your api_key over here
    private final String KEY = "9c7a1417f53de49f373c60caa87b68e8";
    private final String SORT_BY_PARAM = "sort_by";
    public final static String SORT_BY_POPULARITY = "popularity";
    public final static String SORT_BY_HIGHEST_RATED = "vote_average";
    public static String SORTBY = SORT_BY_POPULARITY;

    public URL buildURL(String search, String sortBY) {
        Uri builtUri;
        if (search.isEmpty()) {
            builtUri = Uri.parse(DISCOVER_MOVIES_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, search)
                    .appendQueryParameter(API_KEY_PARAM, KEY)
                    .appendQueryParameter(SORT_BY_PARAM, SORTBY + ".desc")
                    .build();
        } else {
            builtUri = Uri.parse(SEARCH_MOVIES_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, search)
                    .appendQueryParameter(API_KEY_PARAM, KEY)
                    .appendQueryParameter(SORT_BY_PARAM, SORTBY + ".desc")
                    .build();

        }

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("url", "Built URI " + url);

        return url;

    }


    public URL buildURL_Reviews(String movieID) {
        Uri builtUri;
        builtUri = Uri.parse(REVIEWS_MOVIES_URL + movieID + "/reviews").buildUpon()
                .appendQueryParameter(API_KEY_PARAM, KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("url", "Built Reviews URI " + url);

        return url;
    }

    public URL buildURL_Trailer(String movieID) {
        Uri builtUri;
        builtUri = Uri.parse(TRAILER_MOVIES_URL + movieID + "/videos").buildUpon()
                .appendQueryParameter(API_KEY_PARAM, KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("url", "Built Trailer URI " + url);

        return url;
    }


    public String getResultFromURL(URL url) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

}
