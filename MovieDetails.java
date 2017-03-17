package com.spellflight.android.popularmovies;

/**
 * Created by Kim Kirk on 1/25/2017.
 */
public class MovieDetails {
    public String mImage;
    public String mOverview;
    public String mReleaseDate;
    public String mOriginalTitle;
    public String mVoteAverage;


    public MovieDetails (String image, String overview, String releaseDate, String originalTitle, Double voteAverage){
        this.mImage = image;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mOriginalTitle = originalTitle;
        this.mVoteAverage = voteAverage.toString();

    }
}
