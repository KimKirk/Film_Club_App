package com.spellflight.android.popularmovies;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;

/**
 * Created by Kim Kirk on 4/20/2017.
 */
public class MovieContentProvider extends ContentProvider {


    //holds database helper reference
    private MoviesDatabase mDatabaseHelper;


    //mynotes: constants that get assigned integer for AndroidOS's use
    static final int MOVIES = 1;
    static final int STATUS = 2;
    static final int REVIEW = 3;
    static final int TRAILER = 4;
    static final int MOVIE_STATUS = 5;
    static final int MOVIE_REVIEW = 6;
    static final int MOVIE_TRAILER = 7;


    //holds UriMatcher
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //URI matches defined here
    // TODO: 4/26/2017 figure out if there are any other patterns that the incoming URI from the Activity might take and list it here
    //use Evernote Android - Focus: Content Providers topic: fill out a URIMatcher that support the URIs to help with pattern syntax
    static {
        //pattern match for movieid table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_ID, MOVIES);
        //pattern match for statusid table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_STATUS_ID, STATUS);
        //pattern match for reviewsid table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_REVIEW_ID, REVIEW);
        //pattern match for trailerid table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_TRAILER_ID, TRAILER);
        //pattern match for moviestatus table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_STATUS, MOVIE_STATUS);
        //pattern match for moviereviews table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_REVIEWS, MOVIE_REVIEW);
        //pattern match for movietrailers table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_MOVIE_TRAILERS,MOVIE_TRAILER);

    }


    //creates new database helper instance
    @Override
    public boolean onCreate() {
        //get context of app
        Context context = getContext();

        //gets database helper object
        mDatabaseHelper = new MoviesDatabase(context);

        return true;
    }


    //returns data type of a given column's value for that database table
    @Override
    public String getType(Uri uri){
        // Use the Uri Matcher to determine what kind of URI this is.
        final int uriTypeMatch = sURIMatcher.match(uri);
        String type = "";

        //mynotes: write switch statement to return data type of the given columns value based on what type
        //of Uri you got coming in from Activity
        switch (uriTypeMatch) {
            case MOVIES: type = MovieContract.MovieID.CONTENT_TYPE;
            break;
            case STATUS: type =  MovieContract.StatusID.CONTENT_TYPE;
            break;
            case REVIEW: type =  MovieContract.ReviewID.CONTENT_TYPE;
            break;
            case TRAILER: type =  MovieContract.TrailerID.CONTENT_TYPE;
            break;
            case MOVIE_STATUS: type =  MovieContract.MovieStatus.CONTENT_TYPE;
            break;
            case MOVIE_REVIEW: type = MovieContract.MovieReviews.CONTENT_TYPE;
            break;
            case MOVIE_TRAILER: type = MovieContract.MovieTrailers.CONTENT_TYPE;
        }

        return type;
    }


    // TODO: 4/26/2017 write method bodies for all methods below 
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder, CancellationSignal cancellationSignal) {

        //get URIMatcher to find out what URI match has been made
        final int uriDatabaseMatch = sURIMatcher.match(uri);

        //create switch statement that will take the match and compare it to various cases
        //each case performs a specific type of query
        switch(uriDatabaseMatch) {
            case MOVIES:
                break;
            case
        }


        return
    }

    @Override
    public Uri insert (Uri uri, ContentValues values) {

        return
    }


    @Override
    public int update (Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return
    }

    @Override
    public int delete (Uri uri, String selection, String[] selectionArgs) {
        
        return
    }
    
    
}
