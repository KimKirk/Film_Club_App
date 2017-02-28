package com.spellflight.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Kim Kirk on 2/22/2017.
 */
public class MovieDetailsParcel implements Parcelable {
    // TODO: 2/22/2017 test all of this class

    //holds data from moviedetails object
    private String vote;
    private String title;
    private String release;
    private String overview;

    //creates a new MovieDetailsParcel with fields for the data from MovieDetails object
    public MovieDetailsParcel(String vte, String ttl, String rls, String ovrvw) {

        this.vote = vte;
        this.title = ttl;
        this.release = rls;
        this.overview = ovrvw;
    }

    public String getVote(){
        return vote;
    }
    public String getTitle(){
        return title;
    }
    public String getRelease(){
        return release;
    }
    public String getOverview(){
        return overview;
    }


    //constructor used for parcel that reads and sets saved values from parcel
    public MovieDetailsParcel(Parcel in) {
        vote = in.readString();
        title = in.readString();
        release = in.readString();
        overview = in.readString();
    }

    // TODO: 2/22/2017 write comments so you know what this does 
    @Override
    public int describeContents() {
        return 0;
    }

    //write values to parcel to be stored inside the parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vote);
        dest.writeString(title);
        dest.writeString(release);
        dest.writeString(overview);
    }

    //regenerates the object that holds the data so that each time you need to use the object that holds the data, it doesn't create a new one just uses the reference pointer for the parcel
    //used when un-parceling the parcel (creating the object)
    public static final Parcelable.Creator<MovieDetailsParcel> CREATOR =
            new Parcelable.Creator<MovieDetailsParcel>() {
                @Override
                public MovieDetailsParcel createFromParcel(Parcel source) {
                    //creates a new parcel using the parcel input into this method
                    return new MovieDetailsParcel(source);
                }

                @Override
                public MovieDetailsParcel[] newArray(int size) {
                    //if you have an array, it creates a new parcel using the size for the array size
                    return new MovieDetailsParcel[size];
                }
            };
}
