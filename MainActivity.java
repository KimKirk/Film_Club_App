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

/*
 * This class sets default preference, sets custom adapter on layout, sets listener on layout views, starts task on background thread,
 * creates Options Menu
 */

public class MainActivity extends AppCompatActivity {

    //holds instance of Fetch Movie Task
    private FetchMoviesTask mFetchMoviesTask = new FetchMoviesTask();

    //holds custom array adapter
    private MovieImageAdapter mArrayAdapter;

    //holds arraylist of Movie Details objects
    private ArrayList<MovieDetails> mArrayMovieDetails = new ArrayList<MovieDetails>();

    //holds user preference key
    private final String PREFERENCE_KEY = "user preference";


    //holds sharedpreferences
    private SharedPreferences mSharedPreferences;

    //holds sharedpreferences key
    private final String SHARED_PREF_KEY = "movie sort";

    private final String POST_EXEC_TOAST = "No new data from server. Try again in 10 sec.";


    //sets default for preference, sets custom array adapter on gridview layout, sets listener on gridview layout, starts new background task
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //use super method because need to use all its functionality to get onCreate to work correctly
        super.onCreate(savedInstanceState);
        //sets the layout for this activity
        setContentView(R.layout.activity_main);

        //retrieve SharedPreferences that has the current default value for the preference
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //holds editor for editing sharedpreferences
        SharedPreferences.Editor editor;

        //edit the SharedPreferences
        editor = mSharedPreferences.edit();


        //if Bundle is full, retrieve sharedpreferences and set the preference value the user chose
        if(savedInstanceState != null) {
            //edit the sharedpreferences so that it adds the preference value the user chose
            editor.putString(SHARED_PREF_KEY, (String) savedInstanceState.getCharSequence(PREFERENCE_KEY));
        }
        //if Bundle is empty set default value for sharedpreference
        else {
            //holds default sharedpreferences value
            String defaultSharedPreferencesValue = "popular";

            //write the default value to sharedpreferences
            editor.putString(SHARED_PREF_KEY, defaultSharedPreferencesValue);
        }


        //commit the changes to sharedpreferences
        editor.commit();


        //gets the value of sharedpreferences used as input to FetchMovies class
        String mUserSortDefault;
        mUserSortDefault = mSharedPreferences.getString(SHARED_PREF_KEY, "") ;


        //creates new custom Movie Image array adapter
        mArrayAdapter = new MovieImageAdapter(this, R.layout.activity_main, mArrayMovieDetails);

        //holds gridview for activity layout
        GridView mGridView = (GridView) findViewById(R.id.grid_view);

        //sets the array adapter on the layout for the activity so they are bound together
        mGridView.setAdapter(mArrayAdapter);

        //add listener to gridview that listens for user to click on gridview image
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //create anonymous inner class that holds the listener and overrides the onItemClick method as then applies listener to the gridview
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //get position in array adapter
                //get Movie Details object in the adapter at the position
                MovieDetails data = (MovieDetails) mArrayAdapter.getItem(position);

                //create a new Intent that opens the DetailHostActivity
                Intent intent = new Intent(getApplicationContext(), DetailHostActivity.class);

                //add Movie Details object data to the Intent as Extra
                intent.putExtra("movieDetails",new MovieDetailsParcel(data.mVoteAverage,data.mOriginalTitle,data.mReleaseDate,data.mOverview) );

                //start the new Activity passing the Intent to it
                startActivity(intent);
            }
        });

        //starts new task for Fetch Movies Task class sending in user's default preference value
        startTask(mUserSortDefault);
    }


    //creates Options Menu UI
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    //opens new activity based on which menu option user clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //check item id
        switch (item.getItemId()) {
            //check if it's id for settings menu item
            //start new intent to open SettingsHostActivity
            case R.id.settings_menu_item: Intent settingsIntent = new Intent(getApplicationContext(), SettingsHostActivity.class);
                startActivity(settingsIntent);
                return true;

            //check if it's id for about menu item
            //start new intent to open AboutHostActivity
            case R.id.about_menu_item: Intent aboutIntent = new Intent(getApplicationContext(), AboutHostActivity.class);
                startActivity(aboutIntent);
                return true;

            //neither id then just run superclass method and pass in whatever item id it was
            default: return super.onOptionsItemSelected(item);

        }

    }



    //when Activity restarts create new task to update UI with preference value user chose
    @Override
    protected void onRestart(){
        super.onRestart();

        //create new Fetch Movies Task object so we can create a new thread and a new task
        FetchMoviesTask newFetchMoviesTask = new FetchMoviesTask();

        //holds value of sharedpreferences for the preference
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //holds string that gets sent into Fetch Movies Task class for URL update
        String mChangeOut;

        //retrieve value from default SharedPreferences file
        mChangeOut = mSharedPreferences.getString(SHARED_PREF_KEY, "");

        //use this value as input used to create URL that gets data from server
        newFetchMoviesTask.execute(mChangeOut);
    }



    // save the value in the preference that the user last chose
    @Override
    public void onSaveInstanceState (Bundle outState){
        //get user preference value
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //retrieve value from SharedPreferences file
        String prefToSave = mSharedPreferences.getString(SHARED_PREF_KEY, "");

        //save preference data to Bundle
        outState.putCharSequence(PREFERENCE_KEY, prefToSave);

        //save the Bundle
        super.onSaveInstanceState(outState);

    }


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
        }
        //if either no network connection or can't get info on network, let user know by sending them message through toast
        else {Toast toast = Toast.makeText(MainActivity.this,R.string.network_check,Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    /**
     * Created by Kim Kirk on 11/14/2016.
     */

    /*
     * This class executes task on background thread
     */

    public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<MovieDetails>> {
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



        //show progress bar in UI
        @Override
        protected void onPreExecute(){
            //get the progress bar default template that the system creates
            progressBar = (ProgressBar)findViewById(R.id.progress_bar);

            //set the icon to indeterminate
            progressBar.setIndeterminate(true);

            //make the progress bar visible because we need it to be seen at this point
            progressBar.setVisibility(ProgressBar.VISIBLE);

        }


        //does the actual work of the task
        @Override
        protected ArrayList<MovieDetails> doInBackground(String...params) {
            //holds the log message used for exception handling
            String badMethodCall = "bad method call(s)";

            try {
                //call get data from server and get the string that you sent into execute() that was
                dataFromServer = getDataFromServer(params[0]);

                //use the string held inside dataFromServer as input for getDataFromJson
                dataFromJson = getDataFromJson(dataFromServer);

                //if dataFromJson is empty return null so that you don't get an error if dataFromJson is empty
                if (dataFromJson.isEmpty()) {
                    return null;
                }

            } //catches bad method call exception
            catch (Exception ex) {
                //show the exception info in the log
                Log.d(badMethodCall, "doInBackground: ");

                //return null so that you don't get an error for return value when doInBackground() runs and doesn't return a value
                return null;
            }

            //if you don't have any exceptions return arraylist
            return dataFromJson;

        }



        //get data from arraylist and populate the custom array adapter
        @Override
        protected void onPostExecute(ArrayList<MovieDetails> result) {
            if (result != null) {
                //clear out what is already in array adapter
                mArrayAdapter.clear();

                //go through each element in the arraylist and add each element to the array adapter
                ListIterator resultIterator = result.listIterator();

                while (resultIterator.hasNext()) {
                    //add the data at that element in the MovieDetails ArrayList to the mArrayAdapter
                    mArrayAdapter.add(resultIterator.next());
                }
            }
            //if don't receive an ArrayList in the "result" variable, something went wrong so notify user of what steps to take next
            else {
                Toast toast = Toast.makeText(getApplicationContext(), POST_EXEC_TOAST, Toast.LENGTH_SHORT);
                toast.show();
            }

            //we don't need to show the progress bar visibility anymore
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }



        //gets data from JSON object and put into arraylist
        //@return an arraylist with data to populate gridview
        //@parameters string of JSON data
        public ArrayList<MovieDetails> getDataFromJson(String text) throws Exception {

            //holds log message for exception handling
            final String badJson = "check JSON object or JSON array";

            //holds JSONObject that holds a JSONARrray data
            JSONObject jsonArrayJSONObject;

            //put data from the "text" variable into a JSON object
            try {
                //create JSON object populated with JSON data
                JSONObject jsonObject = new JSONObject(text);

                //get the array inside the JSON data; array begins with "results" keyword
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                //go through the length of the JSON array you just got and pull out strings
                for (int i = 0; i < jsonArray.length(); i++) {
                    //use JSON array to get JSON object stored in each element of the JSON array
                    jsonArrayJSONObject = jsonArray.getJSONObject(i);

                    //add that string data from JSON object into the MovieDetails arraylist
                    movieDetailsArrayList.add(new MovieDetails(jsonArrayJSONObject.getString("poster_path"),
                            jsonArrayJSONObject.getString("overview"),
                            jsonArrayJSONObject.getString("release_date"),
                            jsonArrayJSONObject.getString("original_title"),
                            jsonArrayJSONObject.getDouble("vote_average")));
                                    }
            } catch (JSONException jse) {
                //log exception and don't crash the app
                Log.d(badJson, "getDataFromJson: ");

            }

            //check if arraylist is null and return null
            finally {
                if (movieDetailsArrayList.isEmpty()) {
                    //return null so that you don't get an error for return value
                    return null;
                }

                //otherwise if the arraylist is not null return the arraylist
                return movieDetailsArrayList;
            }
        }


        //fetches data from the server
        //@return string of JSON data
        //@parameters string with user preference value
        protected String getDataFromServer(String prefOption) throws Exception {

            //holds log message for exception handling, gives info on specific method to check for error
            final String badUrl = "check URL";
            final String badProtocolRequest = "check setRequestMethod()";
            final String badEncoding = "check InputStreamReader";
            final String badConnectionObject = "check HttpURLConnection";
            final String badIoException = "check connect() or readline()";
            final String nullPointerException = "check connection object for null";

            //holds base URL and value of string that you passed into Fetch Movies Task
            final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/"+prefOption+"?";
            final String API_KEY_PARAM = "api_key";
            final String LANGUAGE_PARAM = "language";
            final String AUTH_KEY = "821d4fff9880f197021eaccba83fb04f";

            //holds URL to be used to fetch data from the server
            URL url = null;

            //holds reader used to read incoming data from input stream into app
            BufferedReader reader;

            //holds input stream
            InputStream inputStream = null;

            //holds HTTP connector
            HttpURLConnection connection = null;

            //holds URI used to build URL
            Uri builtUri;


            //build a new URI to encode the URL properly when it is used as input for HTTP connection
            try {
                builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, AUTH_KEY ).appendQueryParameter(LANGUAGE_PARAM, "en-US")
                        .build();

                //convert the URI you built into a string for HTTP connection
                url = new URL(builtUri.toString());
            }
            catch (MalformedURLException m) {
                //log the exception and don't crash the app
                Log.d(badUrl, "doInBackground: ");
            }

            //opens the http connection and the stream, streams in data
            try {
                //open the HTTP connection using the URL you built
                connection = (HttpURLConnection) url.openConnection();

                //use the HTTP connection to send a GET request to server
                connection.setRequestMethod("GET");

                //connect to the server
                connection.connect();

                //set an input stream onto the connection so you can stream data in
                inputStream = connection.getInputStream();

                //bufferedreader is holding the data from the server
                reader = new BufferedReader((new InputStreamReader(inputStream, "UTF-8")));

                //take data in BufferedReader and convert to a string to be used as input to JSON object in getDataFromJSON method
                rawDataFromServer = reader.readLine();

                //catch multiple exceptions as needed and log the exception without crashing the app
            } catch (ProtocolException pr) {
                Log.d(badProtocolRequest, "getDataFromServer: ");
            } catch (UnsupportedEncodingException uc) {
                Log.d(badEncoding, "getDataFromServer: ");
            } catch (NullPointerException np) {
                Log.d(badConnectionObject, "getDataFromServer: ");
            } catch (IOException io) {
                Log.d(badIoException, "getDataFromServer: ");
            }


            //clean up open stream
            finally {
                try {
                    //close the open stream of there is no input coming from the stream
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                catch (IOException ioe) {
                    //log the exception and don't crash the app
                    Log.d(badIoException, "doInBackground: ");

                    //return null if there is an exception so that you won't get an error when return value
                    return null;
                }



                //close the connection to the server
                try {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                catch (NullPointerException npe){
                    //log the exception and don't crash the app
                    Log.d(nullPointerException, "doInBackground: ");

                    //return null if there is an exception so that you won't get an error when return value
                    return null;

                }


                //return data held inside of the variable "rawDataFromServer" which is the data you received from the stream
                return rawDataFromServer;
            }
        }
    }
}
