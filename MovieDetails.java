package com.spellflight.android.popularmovies;

/**
 * Created by Kim Kirk on 1/25/2017.
 */

/*
 * This class creates Movie Details object
 */

public class MovieDetails {
    //all hold fields for Movie Details object
    public String mImage;
    public String mOverview;
    public String mReleaseDate;
    public String mOriginalTitle;
    public String mVoteAverage;


    //constructor for Movie Details object
    public MovieDetails (String image, String overview, String releaseDate, String originalTitle, Double voteAverage){
        this.mImage = image;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mOriginalTitle = originalTitle;
        this.mVoteAverage = voteAverage.toString();

    }
}
