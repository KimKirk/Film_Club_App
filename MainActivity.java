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

    //// DONE: 1/11/2017 check that these variable names fit with Android syntax convention (when to put mVariableName)
    //holds gridview for activity layout
    private GridView mGridView;

    //holds instance of asynctask
    private FetchMoviesTask mFetchMoviesTask = new FetchMoviesTask();

    //holds the default user value for Preference object
    private String mUserSortDefault = "";

    //holds custom array adapter
    private MovieImageAdapter mArrayAdapter;

    //holds arraylist of moviedetails objects
    private ArrayList<MovieDetails> mArrayMovieDetails = new ArrayList<MovieDetails>();

    //holds user preference key
    private final String PREFERENCE_KEY = "user preference";

    //holds String that gets sent into asynctask class for URL update
    private String mChangeOut = "";

    //holds sharedpreferences file
    private SharedPreferences mSharedPreferences;

    //holds editor for editing sharedpreferences
    private SharedPreferences.Editor mEditor;

    //holds sharedpreferences key
    private final String SHARED_PREF_KEY = "movie sort";

    //holds default sharedpreferences value
    private String mDefaultSharedPreferencesValue = "popular";


    // DONE: 2/10/2017 check all code from all Java classes for unnecessary variables: if only need to use the expression value in line of code in close proximity to expression, get rid of variable that holds the value and just use expression, unless use variable name in another line of code elsewhere in the class
    //TESTING: PASSED
    // TODO: 3/15/2017 make variables local that don't need to be used by other functions/methods in your app
    // TODO: 3/15/2017 if have constants put the name in all caps and use appropriate modifier (FINAL) 
    // TODO: 3/15/2017 move getDataFromServer method so that it is before getDataFromJSON method so that less skipping of lines when run doInBackground method?

    //called by AndroidOS when activity first starts after install app and when activity created after being destroyed
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //use super method because need to use all its functionality to get onCreate to work correctly
        super.onCreate(savedInstanceState);
        //sets the layout for this activity
        setContentView(R.layout.activity_main);

        //in both cases whether the Bundle is full or not you need to get the SharedPreferences file and
        // an editor to edit the SharedPreferences file
        //retrieve SharedPreferences file that has the current default value for the Preferences object
        //get an mEditor so you can make changes to SharedPreferences file
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mEditor = mSharedPreferences.edit();

        //TESTING: PASSED
        // DONE: 1/13/2017 does not use user's current preference
        //checks to see if Bundle is filled with data and if so retrieves the default SharedPreferences file and
        // uses and editor to put the value of the Preference object for the value of SharedPreferences file value
        //making sure the newly created mainactivity has the preference value the user last chose
        if(savedInstanceState != null) {
            //get data from Bundle which will be the value of the Preference the user chose
            //edit the SharedPreference file so that it finds the ListPreference and adds the Preference object value the user chose
            mEditor.putString(SHARED_PREF_KEY, (String) savedInstanceState.getCharSequence(PREFERENCE_KEY));

        }

        //if Bundle is empty means Activity is being run after being destroyed or upon install app
        else {
            //write the default value to SharedPreferences file
            mEditor.putString(SHARED_PREF_KEY, mDefaultSharedPreferencesValue);

        }

        //in both cases you will need to commit the changes made by the Editor
        //commit the changes or they will not be saved
        mEditor.commit();

        //set the string that goes into doInBackground so you can pass that string to create the URL that gets the data from the server
        //uses the value of the SharedPreferences file which should be "popular"
        mUserSortDefault = mSharedPreferences.getString(SHARED_PREF_KEY, "") ;

        //TESTING: PASSED
        // DONE: 12/2/2016 add name of array that holds data
        //holds arrayadapter that is created using the MovieImageAdapter class constructor
        mArrayAdapter = new MovieImageAdapter(this, R.layout.activity_main, mArrayMovieDetails);

        //holds the layout for this activity
        mGridView = (GridView) findViewById(R.id.grid_view);

        //sets the arrayadapter on the layout for the activity so they are bound together
        mGridView.setAdapter(mArrayAdapter);

        //add listener to gridview that listens for user to click on image
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //create anonymous inner class that holds the listener and overrides the onItemClick method as
            // required, then applies listener to the gridview
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //takes in the gridview along with position it has in the arrayadapter
                //get the extra data to be put into the Bundle
                //get position in adapter
                //get object in the adapter at the position
                MovieDetails data = (MovieDetails) mArrayAdapter.getItem(position);

                //create a new Intent
                // DONE: 2/1/2017 get the name of the detail Fragment class and complete this method
                Intent intent = new Intent(getApplicationContext(), DetailHostActivity.class);

                // DONE: 2/23/2017 figure out what the error message means for below line of code
                //add extra data to the Intent

                // DONE: 2/1/2017 get name of Parcelable class and complete this method
                intent.putExtra("movieDetails",new MovieDetailsParcel(data.mVoteAverage,data.mOriginalTitle,data.mReleaseDate,data.mOverview) );

                //Intent opens a new fragment and passes the extra data from the arrayadapter to the fragment
                startActivity(intent);
            }
        });

        //starts the task for Asynctask so that a new thread and new task can begin and do work in
        // background of getting the URL data
        //sends in string to pass to doInBackground to be used to create URL to get data from server
        startTask(mUserSortDefault);
    }


    //TESTING: PASSED
    //this activity needs to have an options menu to hold the settings menu/preference
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //this creates the Menu bar option but does not provide settings fragment items as needed
        //inflates the menu so shows up in UI
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    //TESTING: PASSED
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //gets the id of the item that was clicked on by user
        //if the id is the settings menu item
        if(item.getItemId() == R.id.menu_item) {
            //creates a new intent to open the fragment that has the settings menu/preference
            Intent intent = new Intent(getApplicationContext(), SettingsHostActivity.class);
            startActivity(intent);
            return true;
        }

        //otherwise if the id is not R.id.menu_item then just use the functionality in the superclass method on whatever was clicked
        else {
            return super.onOptionsItemSelected(item);
        }

    }


    // DONE: 1/12/2017 task can only be executed once
    //TESTING: PASSED
    //no information is listed in documentation for Asynctask as to whether or not the thread is
    // killed when the task has completed. I researched this subject through Google and found developers who
    // claim it does thus felt it safe to create a new thread and start a new task.
    //when get back from fragment, need to make sure the images seen on screen match with
    // the settings menu option the user chose
    //don't put this functionality into onStart because you only want to update the value of the
    // string into doInBackground when you return to this activity not when you create this activity as new
    @Override
    protected void onRestart(){
        super.onRestart();

        //create new FetchMoviesTask object so we can create a new thread and a new task because thread can only run one task once
        FetchMoviesTask newFetchMoviesTask = new FetchMoviesTask();

        //holds value of sharedpreference file for the preference object
        // TODO: 3/15/2017 see if can instantiate this member variable at top of source file then just use variable name as needed throughout this Activity
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //retrieve value from default SharedPreferences file
        mChangeOut = mSharedPreferences.getString(SHARED_PREF_KEY, "");

        //use this value as input to execute a new thread and new task and pass the value into
        // doInBackground so can be used to create URL that gets data from server
        newFetchMoviesTask.execute(mChangeOut);
    }


    //TESTING: PASSED
    //making sure to save the value in the Preference object that the user last chose
    @Override
    public void onSaveInstanceState (Bundle outState){
        //get user preference value
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //retrieve default value from SharedPreferences file
        String prefToSave = mSharedPreferences.getString(SHARED_PREF_KEY, "");

        //save preference data to Bundle
        outState.putCharSequence(PREFERENCE_KEY, prefToSave);

        //put this last because you want to send the Bundle into the method as this method saves the
        // Bundle...get the data you want to save then save the Bundle
        super.onSaveInstanceState(outState);

    }


    //TESTING: PASSED
    //checks network connection and starts background thread if connection is good
    public void startTask(String userPreference) {
        //use connectivity manager to get the connectivity service
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //get network info to see if network is active
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //if there is a network object that has data in it from previous line of code AND if
        // the network activity is listed as connected
        if(networkInfo != null && networkInfo.isConnected()) {
            //create the new thread and new task using the string you sent into the startTask method
            mFetchMoviesTask.execute(userPreference);
            // DONE: 12/2/2016 remove line below
        }
        //if either no network connection or can't get info on network, let user know by sending them message through toast
        else {Toast toast = Toast.makeText(MainActivity.this,R.string.network_check,Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    /**
     * Created by Kim Kirk on 11/14/2016.
     */
    //Parameters:
    //String is string value you passed into "execute" method, AsyncTask uses pass by value and
    // puts the value into an array that you have to access in order to use the value, and
    // value is used in doInBackground method in AsyncTask class
    //Void means no value is being passed to onPreExecute method used in AsyncTask class
    //ArrayList is the arraylist full of MovieDetails objects that is used

    //these are part of the signature of the class to tell AndroidOS what data type it can expect you to pass to
    // the methods inside of Asynctask class
    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<MovieDetails>> {
        //// TODO: 1/24/2017 refactor code: check that these variables are initialized correctly
        //holds the data read from the server
        private String rawDataFromServer = null;

        //holds an array of MovieDetails objects of which has the data required to set the text on TextViews in the FragmentDetails UI
        private ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<MovieDetails>();

        //holds data retrieved from the server
        String dataFromServer;

        //holds an array list of MovieDetails objects from the JSON data
        ArrayList<MovieDetails> dataFromJson;

        //holds the progress bar for the UI
        ProgressBar progressBar;


        // DONE: 12/8/2016 see evernote All Tasks "next steps to get adapter working"
        // DONE: 12/8/2016 see evernote All Tasks "getting Settings Preference/Menu Option to show change from
        // "popular" movies to "top rated" movies"

        //TESTING: PASSED
        //show progress bar in UI
        @Override
        protected void onPreExecute(){
            //get the progress bar default template that the system creates
            progressBar = (ProgressBar)findViewById(R.id.progress_bar);

            //set the icon to indeterminate because not sure when data fetching will be completed
            progressBar.setIndeterminate(true);

            //make the progress bar visible because we need it to be seen at this point
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }

        //TESTING: PASSED
        //DONE: check if parameter type is accurate and return type is accurate
        //DONE: 12/9/2016 find out how to throw exception from doInBackground, I think this is why it is not overriding the method
        //this method is called by AndroidOS
        //should return result that onPostExecute() will need as its input parameter
        //does the actual work of the task, work of the task is further broken up into two methods so
        // that it is easier to update those methods without having to change the code inside doInBackground and
        // so that functionality is grouped together

        @Override
        protected ArrayList<MovieDetails> doInBackground(String...params) {
            //holds the log message used for exception handling
            String badMethodCall = "bad method call(s)";

            //the code inside this try block has the ability to produce a method call exception
            try {
                //call get data from server and get the string that you sent into execute() that was
                // passed into the "params" variable that is an array so you must get the string from inside of the array
                dataFromServer = getDataFromServer(params[0]);

                //use the string held inside dataFromServer as input for getDataFromJson because this
                // method requires a string input
                dataFromJson = getDataFromJson(dataFromServer);

                //if dataFromJson is empty return null so that you don't get an error for return value when
                // doInBackground() runs and doesn't return anything
                if (dataFromJson.isEmpty()) {
                    return null;
                }

            } //catch any exception passed to you by holding it in the "ex" variable
            catch (Exception ex) {
                //show the exception info in the log but don't crash the app
                Log.d(badMethodCall, "doInBackground: ");

                //return null so that you don't get an error for return value when doInBackground() runs and doesn't return a value
                return null;
            }

            //if you don't have any exceptions just return whatever dataFromJson is holding, which is
            // the return value from getDataFromJson() which is an arraylist
            return dataFromJson;

        }


        //TESTING: PASSED
        // DONE: 12/9/2016 figure out why this is telling me it isn't overriding
        //this method is called by AndroidOS
        //send in arraylist of moviedetails
        @Override
        protected void onPostExecute(ArrayList<MovieDetails> result) {
            //DONE: CHECK THAT THIS DOES NOT CLEAR THE LIST OF DATA
            if (result != null) {
                //not first time received "result" ArrayList of items which means arrayadapter might have stuff in it already
                //clear out what is already in arrayadapter so can add new stuff no duplicates
                mArrayAdapter.clear();

                //go through each element in the arraylist and add each element to the arrayadapter
                //create an iterator for the "result" variable that holds the ArrayList
                ListIterator resultIterator = result.listIterator();

                while (resultIterator.hasNext()) {
                    //add the data at that element in the MovieDetails ArrayList to the mArrayAdapter
                    //arrayadapter has as each of its elements a MovieDetails object
                    mArrayAdapter.add(resultIterator.next());
                }
            }

            //if don't receive an ArrayList in the "result" variable, something went wrong
            else {
                // DONE: 12/18/2016  add Toast that tells user the data could not be obtained from server...please try again later in 10 - 15 sec
                Toast toast = Toast.makeText(getApplicationContext(), "No new data from server. Try again in 10 sec.", Toast.LENGTH_SHORT);
                toast.show();
            }

            //we don't need to show the progress bar visiblity anymore because the fetching of the data has completed
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }



        //gets data from json object
        //use json object that has json data and pass it into json array so it's easier to get json data out of
        // array, once out of array put into ArrayList with just the data item from the json array that you want
        //should return an arraylist to whomever called it (doInBackground method) so they can use that arraylist as needed
        //TESTING: PASSED
        public ArrayList<MovieDetails> getDataFromJson(String text) throws Exception {

            //holds log message for exception handling
            final String badJson = "check JSON object or JSON array";

            //holds JSONObject that holds a JSONARrray data
            JSONObject jsonArrayJSONObject;

            // "text" variable holds JSON data so need to create JSON object and JSON array to extract needed data
            //pull data from the "text" variable into a JSON object
            try {
                //the code inside this try block could produce a JSON exception if there is a null object that occurs
                //create JsonObject so you can use the methods in the JSON class to do stuff with
                // JSON data, namely get a JSONarray to hold the JSON data
                JSONObject jsonObject = new JSONObject(text);

                //get the array inside the Json data; array begins with "results" keyword
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                //go through the length of the Json array you just got
                for (int i = 0; i < jsonArray.length(); i++) {
                    //use Json array to get Json object stored in each element of the Json array
                    jsonArrayJSONObject = jsonArray.getJSONObject(i);

                    //add that JSON object into the MovieDetails array; use the key to
                    // get the specific value at that key
                    // (e.g. "poster_path is key, value is URL, etc.)
                    movieDetailsArrayList.add(new MovieDetails(jsonArrayJSONObject.getString("poster_path"),
                            jsonArrayJSONObject.getString("overview"),
                            jsonArrayJSONObject.getString("release_date"),
                            jsonArrayJSONObject.getString("original_title"),
                            jsonArrayJSONObject.getDouble("vote_average")));
                                    }
            } catch (JSONException jse) {
                //catch any exceptions that occur and get passed to you by AndroidOS inside of "jse"
                //take that value in "jse" and show it in the log but don't crash the app
                Log.d(badJson, "getDataFromJson: ");

            }

            //this needs to always run even if exception occurs
            //check if arraylist is null that is supposed to hold values you pulled from json data
            finally {
                if (movieDetailsArrayList.isEmpty()) {
                    //return null so that you don't get an error for return value when
                    // doInBackground() runs and doesn't return a value
                    return null;
                }

                //otherwise if the arraylist is not null return the arraylist
                return movieDetailsArrayList;
            }
            //DONE: 3rd - 11/18/2016 fetch the data/images from themoviedb.org need to start http request, need to stream data into program, do I need to create an array that holds the URL for each image? that Picasso then uses in the load() method?
            //DONE: 2nd - after put data into json array figure out how to add to regular array so adapter can use the data? does adapter take json array?
            // DONE: 2nd - 11/10/2016 create array that holds image data from moviedb server, hold in a variable, replace imageArray above with variable name
        }



        //fetches data from the server
        //TESTING: PASSED
        protected String getDataFromServer(String prefOption) throws Exception {

            //holds log message for exception handling, gives info on specific method to check for error
            final String badUrl = "check URL";
            final String badProtocolRequest = "check setRequestMethod()";
            final String badEncoding = "check InputStreamReader";
            final String badConnectionObject = "check HttpURLConnection";
            final String badIoException = "check connect() or readline()";
            final String nullPointerException = "check connection object for null";

            //holds base URL and value of string that you passed into Asynctask
            final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/"+prefOption+"?";
            final String API_KEY_PARAM = "api_key";
            final String LANGUAGE_PARAM = "language";
            final String AUTH_KEY = "821d4fff9880f197021eaccba83fb04f";

            //holds URL to be used to fetch data from the server
            URL url = null;

            //holds reader used to read incoming data from input stream into program
            BufferedReader reader;

            //holds input stream that streams data from server to your computer's memory
            InputStream inputStream = null;

            //holds HTTP connector
            HttpURLConnection connection = null;

            //holds URI used to build URL
            Uri builtUri;


            //the code inside of this try block can produce a malformed URL exception
            //FIXME: key is 821d4fff9880f197021eaccba83fb04f
            //FIXME: 12/2/2016 here is the final url that shows the data you will get from server https://api.themoviedb.org/3/movie/popular?&api_key=821d4fff9880f197021eaccba83fb04f&language=en-US
            //TODO: 11/18/2016 note in a README where it came from, so someone else trying to run your code can create their own key and will quickly know where to put it. Instructors and code reviewers will expect this behavior for any public GitHub code.
            try {
                //build a new URI so that you can encode the URL properly when it is used as input for HTTP connection
                builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, AUTH_KEY ).appendQueryParameter(LANGUAGE_PARAM, "en-US")
                        .build();

                //convert the URI you built into a string because that is what HTTP connection takes as input
                url = new URL(builtUri.toString());
            }
            catch (MalformedURLException m) {//if you have an exception passed to you by AndroidOS save it inside of "m"
                //log the exception and don't crash the app
                Log.d(badUrl, "doInBackground: ");
            }

            //opens the http connection and the stream, streams in data
            //the code inside of this try block can produce an exception and because there are multiple areas
            // in the code that can do that put them all inside one try block for convenience
            try {
                //open the HTTP connectiong using the URL you built
                connection = (HttpURLConnection) url.openConnection();

                //use the HTTP connection to send a GET request to server to let it know you are requesting data
                connection.setRequestMethod("GET");

                //connect to the server
                connection.connect();

                //set an input stream onto the connection so you can stream data into your computer's memory
                inputStream = connection.getInputStream();

                //bufferedreader is holding the data from the server as a field/property inside of the BufferedReader object
                reader = new BufferedReader((new InputStreamReader(inputStream, "UTF-8")));

                //take data in BufferedReader and convert to a string to be used as input to json object in getDataFromJSON method
                rawDataFromServer = reader.readLine();

                //catch all of the possible exception types starting with most specialized to most generic
                //catch each exception inside of its variable and log the exception but don't crash the app
                // TODO: 3/15/2017 think about if need to create more code than just logging the error to make the app do a specific thing in case of exception?
            } catch (ProtocolException pr) {
                Log.d(badProtocolRequest, "getDataFromServer: ");
            } catch (UnsupportedEncodingException uc) {
                Log.d(badEncoding, "getDataFromServer: ");
            } catch (NullPointerException np) {
                Log.d(badConnectionObject, "getDataFromServer: ");
            } catch (IOException io) {
                Log.d(badIoException, "getDataFromServer: ");
            }


            //this always runs
            finally {

                //the code in this try block can produce an exception so must put into try/catch exception handler
                try {
                    //close the open stream of there is no input coming from the stream
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }

                //catch that exception AndroidOS passes to you and save it inside of the "ioe" variable
                catch (IOException ioe) {
                    //log the exception and don't crash the app
                    Log.d(badIoException, "doInBackground: ");

                    //return null if there is an exception so that you won't get an error when there is
                    // no data returned but returned data is required by this method
                    return null;
                }



                //close the connection you opened
                try {
                    //close the connection to the server
                    if(connection != null) {
                        connection.disconnect();
                    }

                } //catch that exception AndroidOS passes to you and save it inside of the "npe" variable
                catch (NullPointerException npe){
                    //log the exception and don't crash the app
                    Log.d(nullPointerException, "doInBackground: ");

                    //return null if there is an exception so that you won't get an error when there is
                    // no data returned but returned data is required by this method
                    return null;

                }


                //return data that is being held inside of the variable "rawDataFromServer" which is the data you received from the stream
                return rawDataFromServer;
            }
        }
    }
}
