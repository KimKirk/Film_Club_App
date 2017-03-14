
package com.spellflight.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Kim Kirk on 2/22/2017.
 */

//this class creates the Parcelable object that is sent between components
public class MovieDetailsParcel implements Parcelable {
    // TODO: 2/22/2017 test all of this class

    //holds data from MovieDetails object that you want to send to another component
    private String vote;
    private String title;
    private String release;
    private String overview;


    //constructor used to create a new MovieDetailsParcel object that only sets fields for MovieDetailsParcel object
    public MovieDetailsParcel(String vte, String ttl, String rls, String ovrvw) {
        //sets the values for the the fields in the MovieDetailsParcel object; data values that come in set the value of the fields
        this.vote = vte;
        this.title = ttl;
        this.release = rls;
        this.overview = ovrvw;
    }

    //create getters so that after you receive the Parcel object in another component you can also get the data values stored inside of it
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


    //constructor used to create a new MovieDetailsParcel object that gets a Parcel passed into it and then reads/sets values for the Parcelable object
    //this is specific to how the Parcelable so that it has meta data needed to recreate the object at destination
    public MovieDetailsParcel(Parcel in) {
        vote = in.readString();
        title = in.readString();
        release = in.readString();
        overview = in.readString();
    }

    // DONE: 2/22/2017 write comments so you know what this does
    //used to describe specifics about contents of Parcel object in case there are special instructions about how to use contents
    @Override
    public int describeContents() {
        //no special instructions so return 0 or "false"
        return 0;
    }

    //write values to Parcel to be stored inside the Parcel
    //this is specific to how the Parcelable works so that it has meta data needed to recreate the object at destination
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vote);
        dest.writeString(title);
        dest.writeString(release);
        dest.writeString(overview);
    }

    //regenerates the Parcel object that holds the data so that each time you need to use the Parcel object, it doesn't create a new one just uses the reference pointer for the current Parcel object
    //created as an anonymous inner class because will only be used inside this class and nowhere else in the program
    public static final Parcelable.Creator<MovieDetailsParcel> CREATOR =
            new Parcelable.Creator<MovieDetailsParcel>() {
                //instantiates the two methods as part of the anonymous inner class
                @Override
                public MovieDetailsParcel createFromParcel(Parcel source) {
                    //creates a new parcel using the parcel input into this method
                    //returns a new MovieDetailsParcel with the "source" parameter as the input for MovieDetailsParcel
                    return new MovieDetailsParcel(source);
                }

                @Override
                public MovieDetailsParcel[] newArray(int size) {
                    //if you have an array, it creates a new Parcel using the "size" parameter for the array size
                    return new MovieDetailsParcel[size];
                }
            };
}
