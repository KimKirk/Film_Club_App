
package com.spellflight.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Kim Kirk on 2/22/2017.
 */

/*
 * This class creates Parcelable to send between components
 */

public class MovieDetailsParcel implements Parcelable {

    //all hold data from MovieDetails object
    private String mVote;
    private String mTitle;
    private String mRelease;
    private String mOverview;


    //constructor used to create a new MovieDetailsParcel object
    public MovieDetailsParcel(String vote, String title, String release, String overview) {
        //sets the values for the the fields in the MovieDetailsParcel object
        this.mVote = vote;
        this.mTitle = title;
        this.mRelease = release;
        this.mOverview = overview;
    }

    //create getters so that after you receive the Parcel object in destination component you can also get the data values
    public String getVote(){
        return mVote;
    }
    public String getTitle(){
        return mTitle;
    }
    public String getReleaseDate(){
        return mRelease;
    }
    public String getOverview(){
        return mOverview;
    }


    //constructor used to create a new MovieDetailsParcel object that gets a Parcel passed into it
    public MovieDetailsParcel(Parcel in) {
        mVote = in.readString();
        mTitle = in.readString();
        mRelease = in.readString();
        mOverview = in.readString();
    }


   //used to describe specifics about contents of Parcel object in case there are special instructions about how to use contents
    @Override
    public int describeContents() {
        //no special instructions so return "false"
        return 0;
    }

    //write values to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mVote);
        dest.writeString(mTitle);
        dest.writeString(mRelease);
        dest.writeString(mOverview);
    }


    //re-creates Parcel object as needed
    public static final Parcelable.Creator<MovieDetailsParcel> CREATOR =
            new Parcelable.Creator<MovieDetailsParcel>() {
                //implements the two methods as part of the anonymous inner class
                @Override
                public MovieDetailsParcel createFromParcel(Parcel source) {
                    //returns a new MovieDetailsParcel with the "source" parameter as the input for MovieDetailsParcel
                    return new MovieDetailsParcel(source);
                }

                @Override
                public MovieDetailsParcel[] newArray(int size) {
                    //if you have an array, creates a new Parcel using the "size" parameter for the array size
                    return new MovieDetailsParcel[size];
                }
            };
}
