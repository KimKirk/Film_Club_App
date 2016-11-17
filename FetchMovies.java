package com.spellflight.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;

import java.net.URI;
import java.net.URL;

/**
 * Created by Kim Kirk on 11/14/2016.
 */
public class FetchMovies extends AsyncTask {

    //TODO: 11/16/2016 finish watching how to create custom arrayadapter in Google+ https://plus.google.com/events/chlh8qqr5q5grs1lajpqnvvql8k?authkey=CNXMrZuHsMWhNg

    /* 1. extend AsyncTask
        2. you will have to override at least one method from the class
            doInBackground()
            most likely you will also override onPostExecute()
        3. the parameters to the AsyncTask class include
            params = data type of parameters sent to the task upon execution of the task
                this is the data type to send into the task and is associated with the doInBackground() method
            progress = the data type of progress units published during the background computation
                this is needed only if you use onProgressUpdate() method inside of the AsyncTask
            Result = data type of result done for this task
                this is usually the data type of the return type from the onPostExecute() method
        4. the methods in the AsyncTask class that can be overriden
            onPreExecute() called in the UI thread before task executes (before you use the execute() method on the task), used to setup the task (like show progress bar in UI)
            doInBackground(params) called on background thread/inside of AsyncTask after onPreExecute finishes executing, used to peform the background task, params listed in AsyncTask class signature are passed into this method, result of this step are returned by this method and passed back to onPostExecute() method, THIS IS WHERE YOU PUT ALL OF THE CODE TO DO WHATEVER THE TASK IS THAT NEEDS TO BE DONE WHICH MEANS THIS METHOD CAN BE HUGE AND IT'S OKAY
            onProgressUpdate(progress) called in UI thread after publishProgress(progress) , used to display any form of progress in UI while background task is executing (like progress bar, etc)
            onPostExecute(result) called in UI thread after background task finishes, result of doInBackground() is passed to this step as a parameter
        5. ground rules for use:
            do not call any of the 4 methods to be overriden manually; just override them and put what you want in the method body, AndroidOS will call them itself as needed in Activity lifecycle
            task can be executed only once (exception will be thrown if second execution is attempted)

        6. create task instance on UI thread
        7. call execute(params) on the task instance
            this starts the background thread */


    @Override
    //TODO: check if parameter type is accurate and return type is accurate
    protected Object doInBackground(Object[] objects) {
        //create URL object
        final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
        //TODO: add API key but make sure to check how to proceed when upload to GIT
        final String API_KEY_PARAM = "api_key";
        final String LANGUAGE_PARAM = "language";

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM,).appendQueryParameter(LANGUAGE_PARAM, "en-US" )
                .build();
        //TODO: add try/catch block to handle this malformed URL exception then error will be removed
        URL url = new URL(builtUri.toString());

    }
}
