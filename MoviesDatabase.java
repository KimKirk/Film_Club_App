package com.spellflight.android.popularmovies;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Kim Kirk on 4/5/2017.
 */

//this class handles creation of database tables and upgrades of the database
public final class MoviesDatabase extends SQLiteOpenHelper {

    //holds version number for database
    public static final int DATABASE_VERSION = 1;
    //holds database name in directory
    public static final String DATABASE_NAME = "movies.db";

    //MYNOTES: just need to use superclass's constructor to pass your database info to it
    //constructor for database
    public MoviesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //creates all database tables and columns
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create MovieID table
        final String SQL_CREATE_MOVIE_ID_TABLE =
                "CREATE TABLE" + MovieContract.MovieID.TABLE_NAME +
                        " (" + MovieContract.MovieID._COUNT + "INTEGER " +
                        MovieContract.MovieID._ID + "INTEGER PRIMARY KEY NOT NULL," +
                        MovieContract.MovieID.COLUMN_TITLE + "STRING NOT NULL," +
                        MovieContract.MovieID.COLUMN_IMAGE + "STRING NOT NULL," +
                        MovieContract.MovieID.COLUMN_OVERVIEW + "STRING NOT NULL," +
                        MovieContract.MovieID.COLUMN_RELEASE_DATE + "STRING NOT NULL," +
                        MovieContract.MovieID.COLUMN_VOTE_AVERAGE + "DOUBLE NOT NULL," + ")";

        //create StatusID table
        final String SQL_CREATE_STATUS_ID_TABLE =
                "CREATE TABLE" + MovieContract.StatusID.TABLE_NAME +
                        " (" +  MovieContract.StatusID._COUNT + "INTEGER," +
                        MovieContract.StatusID._ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        MovieContract.StatusID.COLUMN_STATUS + "STRING NOT NULL," + ")";

        //create ReviewID table
        final String SQL_CREATE_REVIEW_ID_TABLE =
                "CREATE TABLE" + MovieContract.ReviewID.TABLE_NAME +
                        " (" +  MovieContract.ReviewID._COUNT + "INTEGER," +
                        MovieContract.ReviewID._ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        MovieContract.ReviewID.COLUMN_REVIEW + "STRING NOT NULL," + ")";

        //create TrailerID table
        final String SQL_CREATE_TRAILER_ID_TABLE =
                "CREATE TABLE" + MovieContract.TrailerID.TABLE_NAME +
                        " (" +  MovieContract.TrailerID._COUNT + "INTEGER," +
                        MovieContract.TrailerID._ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        MovieContract.TrailerID.COLUMN_TRAILER + "STRING NOT NULL," + ")";

        //create MovieStatus table
        final String SQL_CREATE_MOVIE_STATUS_TABLE =
                "CREATE TABLE" + MovieContract.MovieStatus.TABLE_NAME +
                        "(" +  MovieContract.MovieStatus._COUNT + "INTEGER," +
                        MovieContract.MovieStatus._ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        MovieContract.MovieStatus.COLUMN_MOVIE + "INTEGER NOT NULL, FOREIGN KEY (" +
                            MovieContract.MovieStatus.COLUMN_MOVIE + ") REFERENCES " + MovieContract.MovieID.TABLE_NAME +
                            "(" + MovieContract.MovieID._ID + ")" +
                        MovieContract.MovieStatus.COLUMN_STATUS + "INTEGER NOT NULL, FOREIGN KEY (" +
                            MovieContract.MovieStatus.COLUMN_STATUS + ") REFERENCES " + MovieContract.StatusID.TABLE_NAME +
                            "(" + MovieContract.StatusID._ID + ")" +
                        ")";

        //create MovieReviews table
        final String SQL_CREATE_MOVIE_REVIEWS_TABLE =
                "CREATE TABLE" + MovieContract.MovieReviews.TABLE_NAME +
                        "(" +  MovieContract.MovieReviews._COUNT + "INTEGER," +
                        MovieContract.MovieReviews._ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        MovieContract.MovieReviews.COLUMN_MOVIE_ID + "INTEGER NOT NULL, FOREIGN KEY (" +
                        MovieContract.MovieReviews.COLUMN_MOVIE_ID + ") REFERENCES " + MovieContract.MovieID.TABLE_NAME +
                        "(" + MovieContract.MovieID._ID + ")" +
                        MovieContract.MovieReviews.COLUMN_REVIEW_ID + "INTEGER NOT NULL, FOREIGN KEY (" +
                        MovieContract.MovieReviews.COLUMN_REVIEW_ID + ") REFERENCES " + MovieContract.ReviewID.TABLE_NAME +
                        "(" + MovieContract.ReviewID._ID + ")" +
                        ")";

        //create MovieTrailers table
        final String SQL_CREATE_MOVIE_TRAILERS_TABLE =
                "CREATE TABLE" + MovieContract.MovieTrailers.TABLE_NAME +
                        "(" +  MovieContract.MovieTrailers._COUNT + "INTEGER," +
                        MovieContract.MovieTrailers._ID + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        MovieContract.MovieTrailers.COLUMN_MOVIE_ID + "INTEGER NOT NULL, FOREIGN KEY (" +
                        MovieContract.MovieTrailers.COLUMN_MOVIE_ID + ") REFERENCES " + MovieContract.MovieID.TABLE_NAME +
                        "(" + MovieContract.MovieID._ID + ")" +
                        MovieContract.MovieTrailers.COLUMN_TRAILER_ID + "INTEGER NOT NULL, FOREIGN KEY (" +
                        MovieContract.MovieTrailers.COLUMN_TRAILER_ID + ") REFERENCES " + MovieContract.TrailerID.TABLE_NAME +
                        "(" + MovieContract.TrailerID._ID + ")" +
                        ")";


        //execute the SQL command that creates the table
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_ID_TABLE);
    }


    //this was copied from developer.android.com website
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: 4/6/2017 figure out how to access this variable as it is being defined inside other method
       /* db.execSQL(SQL_CREATE_MOVIE_ID_TABLE);
        onCreate(db);*/
    }
}
