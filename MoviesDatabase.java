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
                        " (" + MovieContract.MovieID._ID + "INTEGER PRIMARY KEY," +
                        MovieContract.MovieID.COLUMN_TITLE + "STRING NOT NULL," +
                        MovieContract.MovieID.COLUMN_IMAGE + "STRING NOT NULL," +
                        MovieContract.MovieID.COLUMN_OVERVIEW + "STRING NOT NULL," +
                        MovieContract.MovieID.COLUMN_RELEASE_DATE + "STRING NOT NULL," +
                        MovieContract.MovieID.COLUMN_VOTE_AVERAGE + "DOUBLE NOT NULL," + ")";

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
