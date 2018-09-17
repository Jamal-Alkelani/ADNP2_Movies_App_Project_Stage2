package com.example.gamal.adnp2_movies_app_project_stage1;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamal.adnp2_movies_app_project_stage1.Adapters.ReviewsAdapter;
import com.example.gamal.adnp2_movies_app_project_stage1.Models.Movie;
import com.example.gamal.adnp2_movies_app_project_stage1.Models.Review;
import com.example.gamal.adnp2_movies_app_project_stage1.Utilities.Movies_JSONHandler;
import com.example.gamal.adnp2_movies_app_project_stage1.Utilities.NetworkHandler;
import com.example.gamal.adnp2_movies_app_project_stage1.database.AppDatabase;
import com.example.gamal.adnp2_movies_app_project_stage1.database.AppExecutors;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;
import java.util.List;


public class Details extends AppCompatActivity {
    public static final String TITLE_TAG = "title";
    public static final String DATE_TAG = "movieDate";
    public static final String IMAGE_TAG = "image";
    public static final String DESC_TAG = "desc";
    public static final String VOTEAVG_TAG = "voteAvg";
    public static final String MOVIE_ID = "movieID";

    private String title;
    private String date;
    private String imageURL;
    private String voteAverage;
    private String desc;
    private Pair<String,String>[] TrailerURLs;
    private ImageView Fav;
    private String movieID;
    private LinearLayout watchTrailer;
    private AppDatabase mDB;
    LifecycleOwner lifecycleOwner;
    private boolean addedToFav;
    private RecyclerView lv;
    private List<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        mDB = AppDatabase.getInstance(this);
        Fav = findViewById(R.id.Fav);
        lv = findViewById(R.id.reviews);

        lifecycleOwner = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(layoutManager);
        lv.setHasFixedSize(true);

        if (intent != null) {
            title = intent.getStringExtra(TITLE_TAG);
            date = intent.getStringExtra(DATE_TAG);
            imageURL = intent.getStringExtra(IMAGE_TAG);
            voteAverage = intent.getStringExtra(VOTEAVG_TAG);
            desc = intent.getStringExtra(DESC_TAG);
            movieID = intent.getStringExtra(MOVIE_ID);
            setTitle(title);
        }
        populateInfo();
        fetchFavMovies(movieID);

        Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (addedToFav) {
                            Movie movie = new Movie();
                            movie.setMovieID(movieID);
                            mDB.movieDao().deleteTask(movie);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Fav.setImageResource(R.drawable.ic_action_heart);
                                    addedToFav = false;
                                }
                            });
                        } else {
                            Movie movie = new Movie();
                            movie.setMovieID(movieID);
                            movie.setTitle(title);
                            movie.setDesc(desc);
                            movie.setMoviePoster(imageURL);
                            movie.setVoteAvg(voteAverage);

                            mDB.movieDao().insertTask(movie);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Fav.setImageResource(R.drawable.ic_action_heart_red);
                                    addedToFav = true;
                                }
                            });
                        }
                    }
                });
            }
        });

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar_Detail);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        watchTrailer = findViewById(R.id.watchTrailer);
        watchTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertMovieTrailers();
            }
        });


        AsyncTask getTrailerURLTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                NetworkHandler networkHandler = new NetworkHandler();
                URL url = networkHandler.buildURL_Trailer(movieID);
                String jsonResponse = networkHandler.getResultFromURL(url);
                Movies_JSONHandler jsonHandler = new Movies_JSONHandler(jsonResponse);
                TrailerURLs = jsonHandler.getMovieURL();

                NetworkHandler networkReviewHandler = new NetworkHandler();
                URL reviewUrl = networkReviewHandler.buildURL_Reviews(movieID);
                String reviewJsonResponse = networkReviewHandler.getResultFromURL(reviewUrl);
                Movies_JSONHandler reviewJsonHandler = new Movies_JSONHandler(reviewJsonResponse);
                reviews = reviewJsonHandler.getMovieReviews();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                ReviewsAdapter adapter = new ReviewsAdapter(reviews);
                lv.setAdapter(adapter);
            }
        };

        getTrailerURLTask.execute();


        initExplodeTrans();
    }
    public void alertMovieTrailers() {
        String items[]=new String[TrailerURLs.length];
        final String urls[]=new String[TrailerURLs.length];
        for (int i=0;i<items.length;i++){
            items[i]=TrailerURLs[i].second;
            urls[i]=TrailerURLs[i].first;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
        builder.setTitle("Available Trailers");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Intent watchTrailer = new Intent(Intent.ACTION_VIEW);
                watchTrailer.setData(Uri.parse(urls[item]));
                startActivity(watchTrailer);
                dialog.dismiss();

            }
        }).show();
    }

    public void fetchFavMovies(final String movieID) {
        LiveData<List<Movie>> movies = mDB.movieDao().loadAllTasks();
        movies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                for (int i = 0; i < movies.size(); i++) {
                    if (movies.get(i).getMovieID().equals(movieID)) {
                        Fav.setImageResource(R.drawable.ic_action_heart_red);
                        addedToFav=true;
                        break;
                    }
                    else {
                        addedToFav=false;
                    }
                }

            }
        });
    }

    private void populateInfo() {
        RatingBar ratingBar = findViewById(R.id.ratingBar_Detail);
        final ScrollView image = findViewById(R.id.imageView_Details);
        TextView voteAvg = findViewById(R.id.voteAvg);
        TextView description = findViewById(R.id.desc_Details);
        TextView title_details = findViewById(R.id.MovieTitle_Detail);
        Picasso.with(this).load(imageURL).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                image.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });

        voteAvg.setText(voteAverage);
        description.setText(desc);
        float rating = Float.parseFloat(voteAverage) / 2;
        ratingBar.setRating(rating);
        title_details.setText(title);

    }


    private void initExplodeTrans() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Transition enterTransXML = TransitionInflater.from(this).inflateTransition(R.transition.transition);
            enterTransXML.setInterpolator(new BounceInterpolator());
            getWindow().setEnterTransition(enterTransXML);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
        return true;
    }


    public void redirectToTrailer(View view) {

    }
}
