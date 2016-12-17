package com.spellflight.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

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
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private FetchMovies posterPath = new FetchMovies();
    private String dummyValue = "top_rated";
    private ImageAdapterView arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TESTING: NOT TESTED
        // DONE: 12/2/2016 add name of array that holds data
        arrayAdapter = new ImageAdapterView(this, R.layout.activity_main, posterPath.getPosterPathArrayList());
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(arrayAdapter);


        startTask(dummyValue);


    }



    //TESTING: PASSED
    //checks network connection and starts background thread if connection is good
    public void startTask(String userPreference) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            posterPath.execute(userPreference);
            // TODO: 12/2/2016 remove line below
            Log.d("Network is up", "startTask: ");
        } else {
            Toast toast = new Toast(MyApplication.getAppContext());
            toast.makeText(MyApplication.getAppContext(),R.string.network_check,Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    //TODO: 11/18/2016 ADD CODE THAT SAYS This product uses the TMDb API but is not endorsed or certified by TMDb. with their logo see this page https://www.themoviedb.org/about/logos-attribution


    /**
     * Created by Kim Kirk on 11/14/2016.
     */
    public class FetchMovies extends AsyncTask<String, Void, ArrayList<String>> {

        private String lineOfText = null;
        private ArrayList posterArray = new ArrayList<String>();
        String dataFromServer;
        ArrayList<String> dataFromJson;



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

        // DONE: 12/8/2016 see evernote All Tasks "next steps to get adapter working"
        // TODO: 12/8/2016 see evernote All Tasks "getting Settings Preference/Menu Option to show change from "popular" movies to "top rated" movies"


        @Override
        //DONE: check if parameter type is accurate and return type is accurate
        //// DONE: 12/9/2016 find out how to throw exception from doInBackground, I think this is why it is not overriding the method
        //should return result that onPostExecute() will need as input param
        protected ArrayList<String> doInBackground(String...params) {

            String badMethodCall = "bad method call(s)";
            try {
                dataFromServer = getDataFromServer();
                dataFromJson = getDataFromJson(dataFromServer);
                if (dataFromJson.isEmpty()) {
                    return null;
                }
            } catch (Exception ex) {
                Log.d(badMethodCall, "doInBackground: ");
                return null;
            }
            return dataFromJson;
            //TODO: 11/18/2016 note in a README where it came from, so someone else trying to run your code can create their own key and will quickly know where to put it. Instructors and code reviewers will expect this behavior for any public GitHub code.
        }


            //this is called by AndroidOS
        // DONE: 12/9/2016 figure out why this is telling me it isn't overriding
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            //DONE: CHECK THAT THIS DOES NOT CLEAR THE LIST OF DATA
            if (result != null) {
                //not first time received result List of items which means arrayadapter has stuff in it already
                //clear out what is already in arrayadapter so can add new stuff no dups
                arrayAdapter.clear();
                //go through each element in the arraylist and add each element to the arrayadapter
                //create an iterator for the result List
                Log.d("check if result null", "onPostExecute: " + result.isEmpty());
                ListIterator resultIterator = result.listIterator();
                while(resultIterator.hasNext())
                //for(String urlPath : result) {

                    //get the element in the current position
                    //add the element to the arrayAdapter
                    arrayAdapter.add(resultIterator.next());
                }

            else {
                //add Toast that tells user the data could not be obtained from server...please try again later in 10 - 15 sec
                Toast toast = new Toast(MyApplication.getAppContext());
                toast.makeText(MyApplication.getAppContext(), "No new data from server", Toast.LENGTH_SHORT);
                toast.show();
            }

        }




        //get poster path arraylist to use in ImageAdapterView class
        public ArrayList<String> getPosterPathArrayList () {
            return posterArray;
        }




        //gets data from json object
        // use json object that has json data and pass it into json array so it's easier to get json data out of array, once out of array put into ArrayList with just the data item from the json array that you want
        //TESTING: PASSED TEST
        //should return an arraylist to whomever called it so they can use that arraylist
        public ArrayList<String> getDataFromJson(String text) throws Exception {

            final String badJson = "check JSON object or JSON array";

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
            } catch (JSONException jse) {
                Log.d(badJson, "getDataFromJson: ");
            } finally {
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
        //TESTING: PASSED TEST
        protected String getDataFromServer() throws Exception {

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

            try {
                //FIXME: key is 821d4fff9880f197021eaccba83fb04f
                // FIXME: 12/2/2016 here is the final url that shows the data you will get from server https://api.themoviedb.org/3/movie/popular?&api_key=821d4fff9880f197021eaccba83fb04f&language=en-US

                builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, "821d4fff9880f197021eaccba83fb04f").appendQueryParameter(LANGUAGE_PARAM, "en-US")
                        .build();
                url = new URL(builtUri.toString());
            }
            catch (MalformedURLException m) {
                Log.d(badUrl, "doInBackground: ");
            }

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

            } catch (ProtocolException pr) {
                Log.d(badProtocolRequest, "getDataFromServer: ");
            } catch (UnsupportedEncodingException uc) {
                Log.d(badEncoding, "getDataFromServer: ");
            } catch (NullPointerException np) {
                Log.d(badConnectionObject, "getDataFromServer: ");
            } catch (IOException io) {
                Log.d(badIoException, "getDataFromServer: ");


            } finally {
                try {
                    if (inputStream != null) {

                        inputStream.close();
                    }
                } catch (IOException ioe) {
                    Log.d(badIoException, "doInBackground: ");
                    return null;
                }
                connection.disconnect();
                return lineOfText;
            }
        }
    }
}
