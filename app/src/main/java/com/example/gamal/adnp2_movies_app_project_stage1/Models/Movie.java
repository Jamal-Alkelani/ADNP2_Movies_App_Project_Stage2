package com.example.gamal.adnp2_movies_app_project_stage1.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey @NonNull
    private String movieID;
    private String title;
    @Ignore
    private String releaseDate;
    private String moviePoster;
    private String voteAvg;
    @Ignore
    private String plotSynopsis;
    private String desc;

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getDesc() {
        return desc;
    }

    public Movie() {

    }

    @Ignore
    public Movie(String title, String releaseDate, String moviePoster, String voteAvg, String plotSynopsis, String desc, String category) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
        this.voteAvg = voteAvg;
        this.plotSynopsis = plotSynopsis;
        this.desc = desc;
        this.category = category;
    }

    public Movie(String title, String releaseDate, String moviePoster, String voteAvg, String plotSynopsis, String desc, String movieID, String category) {
        this.title = title;

        this.releaseDate = releaseDate;
        this.moviePoster = moviePoster;
        this.voteAvg = voteAvg;
        this.plotSynopsis = plotSynopsis;
        this.desc = desc;
        this.movieID = movieID;
        this.category = category;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String category;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(String voteAvg) {
        this.voteAvg = voteAvg;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
