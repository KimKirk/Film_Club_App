package com.spellflight.android.popularmovies;

/**
 * Created by Kim Kirk on 1/25/2017.
 */
public class MovieDetails {
    public String img;
    public String overVw;
    public String releaseDt;
    public String orgTitle;
    public String voteAvg;


    public MovieDetails (String image, String overview, String releaseDate, String originalTitle, Double voteAverage){
        this.img = image;
        this.overVw= overview;
        this.releaseDt = releaseDate;
        this.orgTitle = originalTitle;
        this.voteAvg = voteAverage.toString();

    }
}
