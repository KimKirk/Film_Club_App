package com.spellflight.android.popularmovies;

/**
 * Created by Kim Kirk on 1/25/2017.
 */
public class MovieDetails {
    public String mImg;
    public String mOverVw;
    public String mReleaseDt;
    public String mOrgTitle;
    public String mVoteAvg;


    public MovieDetails (String image, String overview, String releaseDate, String originalTitle, Double voteAverage){
        this.mImg = image;
        this.mOverVw = overview;
        this.mReleaseDt = releaseDate;
        this.mOrgTitle = originalTitle;
        this.mVoteAvg = voteAverage.toString();

    }
}
