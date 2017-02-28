package com.spellflight.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
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

    //// TODO: 1/11/2017 check that these variable names fit with Android syntax convention (when to put mVariableName)
    private GridView gridView;
    private FetchMovies posterPath = new FetchMovies();
    private String userSortDefault = "";
    private MovieImageAdapter arrayAdapter;
    private ArrayList<MovieDetails> arrayOfMovieDetailsObjects = new ArrayList<MovieDetails>();
    private String prefKey = "user preference";
    private String changeOut = "";
    private SharedPreferences defaultSharedPreferences;
    private SharedPreferences.Editor editor;
    private Bundle bundle;

// TODO: 2/10/2017 check all code from all Java classes for unnecessary variables: if only need to use the expression value in line of code in close proximity to expression, get rid of variable that holds the value and just use expression, unless use variable name in another line of code elsewhere in the class 
    //TESTING: PASSED

    //called by AndroidOS when activity first starts after install app and when activity created after being destroyed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //use super method because need to use all its functionality to get onCreate to work correctly
        super.onCreate(savedInstanceState);
        //sets the layout for this activity
        setContentView(R.layout.activity_main);

        //TESTING: PASSED
        //// DONE: 1/13/2017 does not use user's current preference
        //checks to see if Bundle is filled with data and if so...
        if(savedInstanceState != null) {
            //making sure the newly created mainactivity has the preference value the user last chose
            //retrieve default sharedpreferences
            defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            //get an editor so you can make changes to sharedpreferences file
             editor = defaultSharedPreferences.edit();
            //get data from Bundle which will be the value of the preference the user chose
            //edit the sharedpreference object so that it finds the listpreference and adds the preference value the user chose
            editor.putString("movie sort", (String) savedInstanceState.getCharSequence(prefKey));
            //commit the changes or they will not be saved
            editor.commit();

        }
        else {//if Bundle is empty means activity is being run after being destroyed or upon install app
            //set the default value for the sharedpreference file
            //get the sharedpreferences file
            defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            //get an editor so you can make changes to sharedpreferences file
            editor = defaultSharedPreferences.edit();
            //write the default value to sharedpreferences file
            editor.putString("movie sort", "popular");
            //commit the changes or they will not be saved
            editor.commit();

        }
        //set the string that goes into doInBackground so you can pass that string to create the URL that gets the data from the server
        //uses the value of the sharedpreference which should be "popular"
        userSortDefault = defaultSharedPreferences.getString("movie sort", "") ;


        //TESTING: PASSED
        // DONE: 12/2/2016 add name of array that holds data
        //holds arrayadapter that is created using the MovieImageAdapter class constructor
        arrayAdapter = new MovieImageAdapter(this, R.layout.activity_main, arrayOfMovieDetailsObjects);
        //holds the layout for this activity
        gridView = (GridView) findViewById(R.id.grid_view);
        //sets the arrayadapter on the layout for the activity so they are bound together
        gridView.setAdapter(arrayAdapter);
        //add listener to gridview that listens for user to click on image
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //create anonymous class that holds the listener and overrides the onItemClick method as required, then applies listener to the gridview
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //takes in the gridview along with position it has in the arrayadapter

                //get the extra data to be put into the Bundle
                //get position in adapter
                //get object in the adapter at the position
                MovieDetails data = (MovieDetails) arrayAdapter.getItem(position);

                //create a new Intent
                // DONE: 2/1/2017 get the name of the detail Fragment class and complete this method
                Intent intent = new Intent(getApplicationContext(), DetailHostActivity.class);

                // DONE: 2/23/2017 figure out what the error message means for below line of code

                //add extra data to the Intent
                // DONE: 2/1/2017 get name of Parcelable class and complete this method
                intent.putExtra("movieDetails",new MovieDetailsParcel(data.voteAvg,data.orgTitle,data.releaseDt,data.overVw) );

                //Intent opens a new fragment and passes the extra data from the arrayadapter to the fragment
                startActivity(intent);
            }
        });

        //starts the task for Asynctask so that a new thread and new task can begin and do work in background of getting the URL data
        //sends in string to pass to doInBackground to be used to create URL to get data from server
        startTask(userSortDefault);
    }

    //TESTING: PASSED
    //this activity needs to have an options menu to hold the settings menu/preference
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflates the menu so shows up in UI
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //TESTING: PASSED
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //this creates the Menu bar option but does not provide settings fragment items as needed
        //gets the id of the item that was clicked on by user
        switch (item.getItemId()){
            //if the id is the settings menu item
            case R.id.menu_item:
                //creates a new intent to open the fragment that has the settings menu/preference
                Intent intent = new Intent(getApplicationContext(), SettingsHostActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //// DONE: 1/12/2017 task can only be executed once
    //TESTING: PASSED
    //no information is listed in documentation for Asynctask as to whether or not the thread is killed when the task has completed. I researched this subject through Google and found developers who claim it does thus felt it safe to create a new thread and start a new task.
    //when get back from fragment, need to make sure the images seen on screen match with the settings menu option the user chose
    //don't put this functionality into onStart because you only want to update the value of the string into doInBackground when you return to this activity not when you create this activity as new
    @Override
    protected void onRestart(){
        super.onRestart();
        //create new FetchMovies object so we can create a new thread and a new task because thread can only run one task once
        FetchMovies newFetchMoviesTask = new FetchMovies();
        //holds value of sharedpreference file for the preference object
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //retrieve value from default sharedpreferences file
        changeOut = defaultSharedPreferences.getString("movie sort", "");
        //use this value as input to execute a new thread and new task and pass the value into doInBackground so can be used to create URL that gets data from server
        newFetchMoviesTask.execute(changeOut);
    }

    //TESTING: PASSED
    //making sure to save the value of the preference the user last chose
    @Override
    public void onSaveInstanceState (Bundle outState){
        //get user preference value
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //retrieve value from default sharedpreferences
        String prefToSave = defaultSharedPreferences.getString("movie sort", "");
                //getUserPreferenceSelection();
        //save preference data to Bundle
        outState.putCharSequence(prefKey, prefToSave);
        //put this last because you want to send the Bundle into the method as this method saves the Bundle...get the data you want to save then save the Bundle
        super.onSaveInstanceState(outState);

    }


    //TESTING: PASSED
    //checks network connection and starts background thread if connection is good
    public void startTask(String userPreference) {
        //use connectivity manager to get the connectivity service
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //get network info to see if network is active
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //if there is a network object that has data in it from previous line of code AND if the network activity is listed as connected
        if(networkInfo != null && networkInfo.isConnected()) {
            //create the new thread and new task using the string you sent into the startTask method
            posterPath.execute(userPreference);
            // DONE: 12/2/2016 remove line below
        } else {//if either no network connection or can't get info on network, let user know by sending them message through toast
            Toast toast = Toast.makeText(MainActivity.this,R.string.network_check,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //TODO: 11/18/2016 ADD CODE THAT SAYS This product uses the TMDb API but is not endorsed or certified by TMDb. with their logo see this page https://www.themoviedb.org/about/logos-attribution


    /**
     * Created by Kim Kirk on 11/14/2016.
     */
    public class FetchMovies extends AsyncTask<String, Void, ArrayList<MovieDetails>> {//these are part of the signature of the class to tell AndroidOS what data type it can expect you to pass to the methods inside of Asynctask class

        //String is ..., Void is ..., ArrayList is ...

        //// TODO: 1/24/2017 refactor code: check that these variables are initialized correctly
        private String lineOfText = null;
        private ArrayList<MovieDetails> posterArray = new ArrayList<MovieDetails>();
        String dataFromServer;
        ArrayList<MovieDetails> dataFromJson;
        ProgressBar progressBar;


        // DONE: 12/8/2016 see evernote All Tasks "next steps to get adapter working"
        // DONE: 12/8/2016 see evernote All Tasks "getting Settings Preference/Menu Option to show change from "popular" movies to "top rated" movies"

        //TESTING: PASSED
        //show progress bar in UI
        @Override
        protected void onPreExecute(){
            //get the progress bar default template that the system creates
            progressBar= (ProgressBar) findViewById(R.id.progress_bar);
            //set the icon to indeterminate because not sure when data fetching will be completed
            progressBar.setIndeterminate(true);
            //make the progress bar visible because we need it to be seen at this point
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

        //TESTING: PASSED
        @Override
        //DONE: check if parameter type is accurate and return type is accurate
        //DONE: 12/9/2016 find out how to throw exception from doInBackground, I think this is why it is not overriding the method
        //this method is called by AndroidOS
        //should return result that onPostExecute() will need as input param
        //does the actual work of the task, work of the task is further broken up into two methods so that it is easier to update those methods without having to change the code inside doInBackground and so that functionality is grouped together
        protected ArrayList<MovieDetails> doInBackground(String...params) {

            String badMethodCall = "bad method call(s)";
            try {//the code inside this try block has the ability to produce a method call exception
                //call get data from server and get the string that you sent into execute() that was passed into the "params" variable that is an array so you must get the string from inside of the array
                dataFromServer = getDataFromServer(params[0]);
                //use the string held inside dataFromServer as input for getDataFromJson becuase this method requires a string input
                dataFromJson = getDataFromJson(dataFromServer);
                //if dataFromJson is empty return null so that you don't get an error for return value when doInBackground() runs and doesn't return anything
                if (dataFromJson.isEmpty()) {
                    return null;
                }
            } catch (Exception ex) {//catch any exception passed to you by holding it in the "ex" variable
                //show the exception info in the log but don't crash the app
                Log.d(badMethodCall, "doInBackground: ");
                //return null so that you don't get an error for return value when doInBackground() runs and doesn't return a value
                return null;
            }
            //if you don't have any exceptions just return whatever dataFromJson is holding, which is the return value from getDataFromJson() which is an arraylist
            return dataFromJson;
            //TODO: 11/18/2016 note in a README where it came from, so someone else trying to run your code can create their own key and will quickly know where to put it. Instructors and code reviewers will expect this behavior for any public GitHub code.
        }


        //TESTING: PASSED
            //this method is called by AndroidOS
        // DONE: 12/9/2016 figure out why this is telling me it isn't overriding
        @Override
        protected void onPostExecute(ArrayList<MovieDetails> result) {
            //DONE: CHECK THAT THIS DOES NOT CLEAR THE LIST OF DATA
            if (result != null) {
                //not first time received result List of items which means arrayadapter has stuff in it already
                //clear out what is already in arrayadapter so can add new stuff no dups
                arrayAdapter.clear();
                //go through each element in the arraylist and add each element to the arrayadapter
                //create an iterator for the result List
                ListIterator resultIterator = result.listIterator();
                while (resultIterator.hasNext()) {
                    //for(String urlPath : result) {
                    //get the element in the current position
                    //add the element to the arrayAdapter
                    //arrayadapter has as each of its elements a MovieDetails object
                    arrayAdapter.add(resultIterator.next());
                }
            }

            else {
                // DONE: 12/18/2016  add Toast that tells user the data could not be obtained from server...please try again later in 10 - 15 sec
                Toast toast = Toast.makeText(getApplicationContext(), "No new data from server. Try again in 10 sec.", Toast.LENGTH_SHORT);
                toast.show();
            }
            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }


        //gets data from json object
        // use json object that has json data and pass it into json array so it's easier to get json data out of array, once out of array put into ArrayList with just the data item from the json array that you want
        //should return an arraylist to whomever called it so they can use that arraylist
        //TESTING: PASSED
        public ArrayList<MovieDetails> getDataFromJson(String text) throws Exception {

            final String badJson = "check JSON object or JSON array";
            JSONObject jsonArrayJSONObject;

            // bufferedreader holds json so need to create json object and json array to extract needed data
            //pull data from the reader object into a json object
            try {//the code inside this try block could produce a JSON exception if there is a null object that occurs
                //create JsonObject so you can use the methods in the JSON class to do stuff with Json data
                JSONObject jsonObject = new JSONObject(text);
                //get the array inside the Json data; array begins with "results" keyword
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                //go through the length of the Json array you just got
                for (int i = 0; i < jsonArray.length(); i++) {
                    //use Json array to get Json object stored in each element of the Json array
                    jsonArrayJSONObject = jsonArray.getJSONObject(i);
                    //go into object and use key to get value
                    //get String overview and put into variable
                    // get String releaseDate and put into variable
                    // get String originalTitle and put into variable
                    // get Float voteAverage and put into variable
                    /*String posterPath = jsonArrayJSONObject.getString("poster_path");
                    String posterPath = jsonArrayJSONObject.getString("overview");
                    String posterPath = jsonArrayJSONObject.getString("release_date");
                    String posterPath = jsonArrayJSONObject.getString("original_title");
                    String posterPath = jsonArrayJSONObject.getDouble("vote_average");*/

                    /*Log.d("toString check", "doInBackground: " + "posterPath");*/
                    //pass variables with values into new MovieDetails object
                    /*new MovieDetails(jsonArrayJSONObject.getString("poster_path"), jsonArrayJSONObject.getString("overview"), jsonArrayJSONObject.getString("release_date"), jsonArrayJSONObject.getString("original_title"), jsonArrayJSONObject.getDouble("vote_average"));
                    //put object into arraylist
                    //posterArray.add(moviedetails object);*/
                    posterArray.add(new MovieDetails(jsonArrayJSONObject.getString("poster_path"), jsonArrayJSONObject.getString("overview"), jsonArrayJSONObject.getString("release_date"), jsonArrayJSONObject.getString("original_title"), jsonArrayJSONObject.getDouble("vote_average")));
                    Log.d("arrayList check", "doInBackground: " + posterArray.get(i));
                }
            } catch (JSONException jse) {//catch any exceptions that occur and get passed to you by AndroidOS inside of "jse"
                //take that value in "jse" and show it in the log but don't crash the app
                Log.d(badJson, "getDataFromJson: ");
            } finally {//this needs to always run
                if (posterArray.isEmpty()) {//check if arraylist is null that is supposed to hold values you pulled from json data
                    //rreturn null so that you don't get an error for return value when doInBackground() runs and doesn't return a value
                    return null;
                }//otherwise if the arraylist is not null return the arraylist
                return posterArray;
            }
            //DONE: 3rd - 11/18/2016 fetch the data/images from themoviedb.org need to start http request, need to stream data into program, do I need to create an array that holds the URL for each image? that Picasso then uses in the load() method?
            //DONE: 2nd - after put data into json array figure out how to add to regular array so adapter can use the data? does adapter take json array?
            // DONE: 2nd - 11/10/2016 create array that holds image data from moviedb server, hold in a variable, replace imageArray above with variable name
        }





        //fetches data from the server
        //TESTING: PASSED
        protected String getDataFromServer(String prefOption) throws Exception {

            final String badUrl = "check URL";
            final String badProtocolRequest = "check setRequestMethod()";
            final String badEncoding = "check InputStreamReader";
            final String badConnectionObject = "check HttpURLConnection";
            final String badIoException = "check connect() or readline()";
            //holds base URL and value of string that you passed into Asynctask
            final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/"+prefOption+"?";
            final String API_KEY_PARAM = "api_key";
            final String LANGUAGE_PARAM = "language";
            URL url = null;
            BufferedReader reader;
            InputStream inputStream = null;
            HttpURLConnection connection = null;
            Uri builtUri;

            try {//the code inside of this try block can produce a malformed URL exception
                //FIXME: key is 821d4fff9880f197021eaccba83fb04f
                // FIXME: 12/2/2016 here is the final url that shows the data you will get from server https://api.themoviedb.org/3/movie/popular?&api_key=821d4fff9880f197021eaccba83fb04f&language=en-US

                //build a new URI so that you can encode the URL properly when it is used as input for HTTP connection
                builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, "821d4fff9880f197021eaccba83fb04f").appendQueryParameter(LANGUAGE_PARAM, "en-US")
                        .build();
                //convert the URI you built into a string because that is what HTTP connection takes as input
                url = new URL(builtUri.toString());
            }
            catch (MalformedURLException m) {//if you have an exception passed to you by AndroidOS save it inside of "m"
                //log the exception and don't crash the app
                Log.d(badUrl, "doInBackground: ");
            }

            //opens the http connection and the stream, streams in data
            try {//the code inside of this try block can produce an exception and because there are multiple areas in the code that can do that put them all inside one try block for convenience
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                inputStream = connection.getInputStream();

                //bufferedreader is holding the data from the server
                reader = new BufferedReader((new InputStreamReader(inputStream, "UTF-8")));

                //take data in reader and convert to a string to be used as input to json object in other method
                lineOfText = reader.readLine();

                //catch all of the possible exception types starting with most specialized to most generic
            } catch (ProtocolException pr) {
                Log.d(badProtocolRequest, "getDataFromServer: ");
            } catch (UnsupportedEncodingException uc) {
                Log.d(badEncoding, "getDataFromServer: ");
            } catch (NullPointerException np) {
                Log.d(badConnectionObject, "getDataFromServer: ");
            } catch (IOException io) {
                Log.d(badIoException, "getDataFromServer: ");


            } finally {//this always runs
                try {//the code in this try block can produce an exception
                    if (inputStream != null) {//close the open stream of there is no input coming from the stream

                        inputStream.close();
                    }
                } catch (IOException ioe) {//catch that exception AndroidOS passes to you and save it inside of the "ioe" variable
                    //log the exception and don't crash the app
                    Log.d(badIoException, "doInBackground: ");
                    //return null if there is an exception so that you won't get an error when there is no data returned but returned data is required by this method
                    return null;
                }//close the connection you opened
                connection.disconnect();
                //return data that is being held inside of the variable the data you received from the stream
                return lineOfText;
            }
        }
    }
}
