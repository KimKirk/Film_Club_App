package com.spellflight.android.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Kim Kirk on 4/6/2017.
 */

//this class provides the data contract for Content Provider
public final class MovieContract {

    //holds content authority to be used in URIs
    public static final String CONTENT_AUTHORITY = "com.spellflight.android.popularmovies";

    //holds base content URI
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //private constructor so no one can instantiate this class
    private MovieContract() {

    }


    //path to MovieID table
    public static final String PATH_MOVIE_ID = "movie_id";
    //path to StatusID table
    public static final String PATH_STATUS_ID = "status_id";
    //path to ReviewID table
    public static final String PATH_REVIEW_ID = "reviews_id";
    //path to TrailerID table
    public static final String PATH_TRAILER_ID = "trailer_id";
    //path to MovieStatus table
    public static final String PATH_MOVIE_STATUS = "movie_status";
    //path to MovieReviews table
    public static final String PATH_MOVIE_REVIEWS = "movie_reviews";
    //path to MovieTrailers table
    public static final String PATH_MOVIE_TRAILERS = "movie_trailers";




    //MYNOTES: static for all inner classes so I don't have to use a reference to the outer class when
    //I want to use the inner class members in another class
    public static final class MovieID implements BaseColumns {
        public static final String TABLE_NAME = "movie_id";
        public static final String _COUNT = "count";
        //MYNOTES
        //this is the "id" element from the JSON array
        //this is integer primary key
        public static final String _ID = "movie_id";
        //movie title stored as a String
        public static final String COLUMN_TITLE = "original_title";
        //movie image url stored as a String
        public static final String COLUMN_IMAGE = "movie_image";
        //movie overview stored as a String
        public static final String COLUMN_OVERVIEW = "overview";
        //movie release data stored as a String
        public static final String COLUMN_RELEASE_DATE = "release_date";
        //movie voter average rating stored as a double
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        //uri

    }

    //creates labels for table name and columns in StatusID table
    public static final class StatusID implements BaseColumns {
        public static final String TABLE_NAME = "status_id";
        public static final String _COUNT = "count";
        //this is primary key - autoincrement
        public static final String _ID = "status_id";
        //movie list status "popular", "favorite", "top rated" stored as a String
        public static final String COLUMN_STATUS = "status";

        //uri

    }

    //creates labels for table name and columns in ReviewID table
    public static final class ReviewID implements BaseColumns {
        public static final String TABLE_NAME = "review_id";
        public static final String _COUNT = "count";
        //this is primary key - autoincrement
        public static final String _ID = "review_id";
        //movie review URL stored as a String
        public static final String COLUMN_REVIEW = "review";

        //uri

    }

    //creates labels for table name and columns in TrailerID table
    public static final class TrailerID implements BaseColumns {
        public static final String TABLE_NAME = "trailer_id";
        public static final String _COUNT = "count";
        //this is primary key - autoincrement
        public static final String _ID = "trailer_id";
        //movie trailer URL stored as a String
        public static final String COLUMN_TRAILER = "trailer";

        //uri

    }

    //creates labels for table name and columns in MovieStatus table
    public static final class MovieStatus implements BaseColumns {
        public static final String TABLE_NAME = "movie_status";
        public static final String _COUNT = "count";
        //movie id stored as an integer
        public static final String COLUMN_MOVIE = "movie_id";
        //movie status id stored as an integer
        public static final String COLUMN_STATUS = "status_id";
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";

        //uri
    }

    //creates labels for table name and columns in MovieReviews table
    public static final class MovieReviews implements BaseColumns{
        public static final String TABLE_NAME = "movie_reviews";
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";
        public static final String _COUNT = "count";
        //movie id stored as an integer
        public static final String COLUMN_MOVIE_ID = "movie_id";
        //movie review id stored as an integer
        public static final String COLUMN_REVIEW_ID = "review_id";

        //uri
    }

    //creates labels for table name and columns in MovieTrailers table
    public static final class MovieTrailers implements BaseColumns {
        public static final String TABLE_NAME = "movie_trailers";
        public static final String _COUNT = "count";
        //movie id stored as an integer
        public static final String COLUMN_MOVIE_ID = "movie_id";
        //movie trailer id stored as an integer
        public static final String COLUMN_TRAILER_ID = "trailer_id";
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";

        //uri
    }


    //DESIGN: update this so it allows you to switch out path constant based on what goes
    // in as string value then used in switch to determine which path constant gets used
    // TODO: 4/5/2017 figure out what is going to call this method and if that caller can pass in a string to this method
    // to be used to determine switch case to use
    public Uri uriBuilder () {
        return BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_STATUS).build();
    }


}
