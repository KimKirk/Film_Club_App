package com.spellflight.android.popularmovies;

import android.content.ContentResolver;
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
    public static final String PATH_MOVIE_ID = "movieId";
    //path to MovieStatus table
    public static final String PATH_MOVIE_STATUS = "movieStatus";
    //path to MovieReviews table
    public static final String PATH_MOVIE_REVIEWS = "movieReviews";
    //path to MovieTrailers table
    public static final String PATH_MOVIE_TRAILERS = "movieTrailers";


    // DONE: 4/5/2017 figure out what is going to call this method and if that caller can pass in a string to this method
    //builds moviestatus content uri
    public Uri  buildMovieStatusUri () {
        return BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_STATUS).build();
    }

    //builds movieid content uri
    public Uri  buildMovieIdUri () {
        return BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_ID).build();
    }

    //builds moviereviews content uri
    public Uri  buildMovieReviewsUri () {
        return BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_REVIEWS).build();
    }

    //builds movietrailer content uri
    public Uri  buildMovieTrailerUri () {
        return BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_TRAILERS).build();
    }


    //MYNOTES: static for all inner classes so I don't have to use a reference to the outer class when
    //I want to use the inner class members in another class
    public static final class MovieID implements BaseColumns {

        public static final String TABLE_NAME = "movieId";
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

        //holds the data type of the given columns content for the table
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_ID;
        //holds the data type of the given columns content for the single row
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_ID;

    }

    //creates labels for table name and columns in MovieStatus table
    public static final class MovieStatus implements BaseColumns {
        public static final String TABLE_NAME = "movieStatus";
        public static final String _COUNT = "count";
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";
        //movie id from MovieID table stored as an integer
        public static final String COLUMN_MOVIE = "movie_id";
        ////movie list status "popular", "favorite", "top rated" stored as a String
        public static final String COLUMN_STATUS = "status";

        // DONE: 4/26/2017 add reference to data type that this table has so that when you use gettype() method it knows
        // DONE: 4/26/2017 do this for all other database tables as well
        //mynotes: what data type gets returned for that database table
        //The data type for each column in a provider is usually listed in its documentation.
        /*Providers also maintain MIME data type information for each content URI they define.
        You can use the MIME type information to find out if your application can handle data
        that the provider offers, or to choose a type of handling based on the MIME type.
        You usually need the MIME type when you are working with a provider that contains
        complex data structures or files. For example, the ContactsContract.
        Data table in the Contacts Provider uses MIME types to label
        the type of contact data stored in each row. To get the MIME type corresponding to a content URI, call ContentResolver.getType().*/

        //holds the data type of the given columns content for the table
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_STATUS;
        //holds the data type of the given columns content for the single row
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_STATUS;


    }

    //creates labels for table name and columns in MovieReviews table
    public static final class MovieReviews implements BaseColumns{
        public static final String TABLE_NAME = "movieReviews";
        public static final String _COUNT = "count";
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";
        //movie id stored as an integer
        public static final String COLUMN_MOVIE_ID = "movie_id";
        //movie review URL stored as a String
        public static final String COLUMN_REVIEW = "review";

        //holds the data type of the given columns content for the table
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_REVIEWS;
        //holds the data type of the given columns content for the single row
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_REVIEWS;


    }

    //creates labels for table name and columns in MovieTrailers table
    public static final class MovieTrailers implements BaseColumns {
        public static final String TABLE_NAME = "movieTrailers";
        public static final String _COUNT = "count";
        //primary key is required on SQLite database tables
        public static final String _ID = "primary_key";
        //movie id stored as an integer
        public static final String COLUMN_MOVIE_ID = "movie_id";
        //movie trailer URL stored as a String
        public static final String COLUMN_TRAILER = "trailer";

        //holds the data type of the given columns content for the table
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_TRAILERS;
        //holds the data type of the given columns content for the single row
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_TRAILERS;
    }

}
