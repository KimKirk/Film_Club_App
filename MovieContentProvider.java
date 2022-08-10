/*
package com.spellflight.android.popularmovies;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.preference.PreferenceManager;

*/
/**
 * Created by Kim Kirk on 4/20/2017.
 *//*


public abstract class MovieContentProvider extends ContentProvider {


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

    //create a querybuilder and use setTables() to create the join for the database tables
    private static final SQLiteQueryBuilder sMoviebyStatusQueryBuilder;

    //creates a new sqlquerybuilder when class is loaded
    static{
        sMoviebyStatusQueryBuilder = new SQLiteQueryBuilder();

        //// TODO: 5/3/2017 should join all tables together so it sets list of tables to query and then can refer to it for any switch case needed for crud operations
        //mynotes: you need to join all of the tables together as soon as this class is loaded that way you can just create the where clause in the switch below in the query() as needed and don't have to create new joins for each time you need to join tables
        //joins all database tables on class loading
        sMoviebyStatusQueryBuilder.setTables(
                MovieContract.MovieID.TABLE_NAME +
                        " INNER JOIN " + MovieContract.MovieStatus.TABLE_NAME +
                        " ON " + MovieContract.MovieID.TABLE_NAME + "." + MovieContract.MovieID._ID + " = " +
                        MovieContract.MovieStatus.TABLE_NAME + "." + MovieContract.MovieStatus.COLUMN_MOVIE +
                        "INNER JOIN " + MovieContract.MovieReviews.TABLE_NAME +
                        " ON " + MovieContract.MovieStatus.TABLE_NAME + "." + MovieContract.MovieStatus.COLUMN_MOVIE + " = " +
                         MovieContract.MovieReviews.TABLE_NAME + "." + 	MovieContract.MovieReviews.COLUMN_MOVIE_ID +
                        "INNER JOIN " + MovieContract.MovieTrailers.TABLE_NAME +
                        " ON " + MovieContract.MovieReviews.TABLE_NAME + "." + MovieContract.MovieReviews.COLUMN_MOVIE_ID + " = " +
                        MovieContract.MovieTrailers.TABLE_NAME + "." + MovieContract.MovieTrailers.COLUMN_MOVIE_ID);
    }



    //holds UriMatcher
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //URI matches defined here
    // TODO: 4/26/2017 figure out if there are any other patterns that the incoming URI from the Activity might take and list it here
    //use Evernote Android - Focus: Content Providers topic: fill out a URIMatcher that support the URIs to help with pattern syntax
    static {
        //pattern match for movieid table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_ID, MOVIES);
        //pattern match for moviestatus table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_STATUS, MOVIE_STATUS);
        //pattern match for moviereviews table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE_REVIEWS, MOVIE_REVIEW);
        //pattern match for movietrailers table
        sURIMatcher.addURI(MovieContract.CONTENT_AUTHORITY,MovieContract.PATH_MOVIE_TRAILERS,MOVIE_TRAILER);
        //pattern match for

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
            case MOVIE_STATUS: type =  MovieContract.MovieStatus.CONTENT_TYPE;
            break;
            case MOVIE_REVIEW: type = MovieContract.MovieReviews.CONTENT_TYPE;
            break;
            case MOVIE_TRAILER: type = MovieContract.MovieTrailers.CONTENT_TYPE;
        }

        return type;
    }


    //mynotes: this provides the full query to get MovieID columns where status = sharedpreferences string value
    //mynotes: you have already provided the querybuilder with the returned tables from the join, now just query those tables with a where clause
    //mynotes: this is the where clause of the sql statement, this will go into the "selection" input in the query() method
    //mynotes: the ? signifies where placeholder, you provide value to replace placeholder on runtime (this values is the "selectionArgs input in query()
    String sMovieImageQuery = MovieContract.MovieStatus.TABLE_NAME + "." +
            MovieContract.MovieStatus.COLUMN_STATUS + "= ? ";

    //mynotes: this is the sharedpreferences provider that has the sharedpreferences string you need to provide for the query to retrieve the correct rows
    //get sharedpreferences string


    // TODO: 4/26/2017 write method bodies for all methods below 
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder, CancellationSignal cancellationSignal) {

        //get URIMatcher to find out what URI match has been made
        final int uriDatabaseMatch = sURIMatcher.match(uri);

        //create switch statement that will take the match and compare it to various cases
        //each case performs a specific type of query
        switch(uriDatabaseMatch) {
            // TODO: 5/3/2017 make sure this uri match is the best pattern for this query so no problems when class object sends uri for that query 
            case MOVIES: //use same querybuilder object to call query() that will allow you to build your query and returns a cursor

                // TODO: 5/3/2017 think about turning this into a helper method where you create the helper method elsewhere in this class then just call the helper method inside this switch, do this for other queries as well
                //returns all rows where COLUMN_STATUS = SharedPreferences string from the joined tables, used in MainActivity to populate movie images and return data to pass to MovieDetailsFragment
                sMoviebyStatusQueryBuilder.query(mDatabaseHelper.getReadableDatabase(), projection, sMovieImageQuery,
                        new String[] {"sharedPreferencesStringGoesHere"}, null, null, null);

                break;

        }

        return ;//return a cursor back to caller, caller iterates through cursor to retrieve data in cursor
    }

    @Override
    public Uri insert (Uri uri, ContentValues values) {
        // TODO: 5/4/2017 carry out the following tasks below 
        //get reference to writeable database in here to cut down on lag time, don't put this in onCreate() method

        */
/*for "favorites" status
	*       user marks favorite using star ImageView
	*
		*   find movieid in MovieStatus table and insert a row with movieid and status = "favorite"
		*       check to see if already have as favorite?
		*
			*       go to _ID column to check movie id
			*       if COLUMN_STATUS = "favorite" do not insert
			*       if COLUMN_STATUS != "favorite" insert
			*       *//*


        //





        return
    }


    @Override
    public int update (Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO: 5/4/2017 figure out what to do with this; don't need to use it

        return
    }

    @Override
    public int delete (Uri uri, String selection, String[] selectionArgs) {
        // TODO: 5/4/2017 carry out the following tasks below 
        //use URIMatcher to decide which action to take for deletion based on URI sent in

        //get reference to open writeable database and call delete() on it from database class

        //pass into delete() method from database object the inputs received from delete() method in ContentProvider class
            //delete() method from database class uses the inputs to make the deletion in the database table


        
        return
    }
    
    
}
*/
