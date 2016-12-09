package com.spellflight.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kim Kirk on 11/14/2016.
 */
public class FetchMovies extends AsyncTask <Void, Void, Void> {

    private String lineOfText = null;
    private ArrayList posterArray = new ArrayList();
    


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
            doInBackground(params) called on background thread/inside of AsyncTask after onPreExecute finishes executing, used to peform the background task, params listed in AsyncTask class signature are passed into this method, result of this step are returned by this method and passed back to onPostExecute() method, THIS IS WHERE YOU PUT ALL OF THE CODE TO DO WHATEVER THE TASK IS THAT NEEDS TO BE done WHICH MEANS THIS METHOD CAN BE HUGE AND IT'S OKAY
            onProgressUpdate(progress) called in UI thread after publishProgress(progress) , used to display any form of progress in UI while background task is executing (like progress bar, etc)
            onPostExecute(result) called in UI thread after background task finishes, result of doInBackground() is passed to this step as a parameter
        5. ground rules for use:
            do not call any of the 4 methods to be overriden manually; just override them and put what you want in the method body, AndroidOS will call them itself as needed in Activity lifecycle
            task can be executed only once (exception will be thrown if second execution is attempted)

        6. create task instance on UI thread
        7. call execute(params) on the task instance
            this starts the background thread */

    // TODO: 12/8/2016 see evernote All Tasks "next steps to get adapter working" 
    // TODO: 12/8/2016 see evernote All Tasks "getting Settings Preference/Menu Option to show change from "popular" movies to "top rated" movies" 


    @Override
    //TODO: check if parameter type is accurate and return type is accurate
    //should return result that onPostExecute() will need as input param
    protected List doInBackground(Params...params) throws Exception {

       String dataFromServer = getDataFromServer();
        List dataFromJson = getDataFromJson(dataFromServer);
        return dataFromJson;

        //TODO: 11/18/2016 note in a README where it came from, so someone else trying to run your code can create their own key and will quickly know where to put it. Instructors and code reviewers will expect this behavior for any public GitHub code.
    }




    //get poster path arraylist to use in ImageAdapterView class
    public List getPosterPathArrayList () {
        return posterArray;
    }




    //gets data from json object
    // use json object that has json data and pass it into json array so it's easier to get json data out of array, once out of array put into ArrayList with just the data item from the json array that you want

    //TESTING: FULL METHOD PASSED
    //should return an arraylist to whomever called it so they can use that arraylist
    public List getDataFromJson (String text) throws Exception {

        final String badJson = "check JSON object or JSON array";

        /*Json object has a json array inside of it
        json array is= results
        element 0 = poster_path
        element 1= poster_path*/

        // bufferedreader holds json so need to create json object and json array to extract needed data
        //pull data from the reader object into a json object
        try {
            JSONObject jsonObject = new JSONObject(text);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonArrayJSONObject = jsonArray.getJSONObject(i);
                //go into object and use key to get value
                String value = jsonArrayJSONObject.getString("poster_path");
                Log.d("toString check", "doInBackground: " + value);
                //put each String equivalent into arraylist
                posterArray.add(value);
                Log.d("arrayList check", "doInBackground: " + posterArray.get(i));
            }
        }
        catch (JSONException jse) {
            Log.d(badJson, "getDataFromJson: ");
        }
        finally {
                if (posterArray.isEmpty()) {
                    return null;
                }
            return posterArray;
        }

        //DONE: 3rd - 11/18/2016 fetch the data/images from themoviedb.org need to start http request, need to stream data into program, do I need to create an array that holds the URL for each image? that Picasso then uses in the load() method?
        //DONE: 2nd - after put data into json array figure out how to add to regular array so adapter can use the data? does adapter take json array?
        // DONE: 2nd - 11/10/2016 create array that holds image data from moviedb server, hold in a variable, replace imageArray above with variable name
        // DESIGN: 12/2/2016 add conditional statement so that you can choose which data to pull from the array so that you can make this method public and use the results in ImageAdapterView class
    }



    //does the work normally inside of doInBackground but put the work into own method so is cleaner and easier to modify in future and won't affect doInBackground
    //fetches data from the server
    //TESTING: FULL METHOD PASSED
    protected String getDataFromServer() throws Exception  {
        //DONE: check variables here to see if they need access modifier private or not
        //DONE: add API key but make sure to check how to proceed when upload to GIT
        final String badUrl = "check URL";
        final String badProtocolRequest = "check setRequestMethod()";
        final String badEncoding = "check InputStreamReader";
        final String badConnectionObject = "check HttpURLConnection";
        final String badIoException = "check connect() or readline()";
        //create URL object
        final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
        final String API_KEY_PARAM = "api_key";
        final String LANGUAGE_PARAM = "language";
        URL url = null;
        BufferedReader reader;
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        Uri builtUri;


        //TESTING: PASSED
        try {
            //FIXME: key is 821d4fff9880f197021eaccba83fb04f
            // FIXME: 12/2/2016 here is the final url that shows the data you will get from server https://api.themoviedb.org/3/movie/popular?&api_key=821d4fff9880f197021eaccba83fb04f&language=en-US

            builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, " ").appendQueryParameter(LANGUAGE_PARAM, "en-US")
                    .build();
            url = new URL(builtUri.toString());
        } catch (MalformedURLException m) {
            Log.d(badUrl, "doInBackground: ");
            System.out.println(badUrl);
        }


        //TESTING: PASSED
        //opens the http connection and the stream, streams in data
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();

            //bufferedreader is holding the data from the server
            reader = new BufferedReader((new InputStreamReader(inputStream, "UTF-8")));

            //take data in reader and convert to a string to be used as input to json object in other method
            lineOfText = reader.readLine();

            // DONE: 12/2/2016 check for condition that would create IOException, this will propogate the exception to another method with catch block for IOException
            }
        catch (ProtocolException pr) {
            Log.d( badProtocolRequest , "getDataFromServer: ");
        }
        catch (UnsupportedEncodingException uc) {
            Log.d(badEncoding, "getDataFromServer: ");
        }
        catch (NullPointerException np) {
            Log.d(badConnectionObject, "getDataFromServer: ");
        }
        catch (IOException io) {
            Log.d(badIoException, "getDataFromServer: ");


        } finally {
            try{
                if (inputStream != null) {

                    inputStream.close();
                    return null;
                }
            }
            catch (IOException ioe) {
                Log.d(badIoException, "doInBackground: ");
            }
            connection.disconnect();
            return lineOfText;
        }
    }
}


