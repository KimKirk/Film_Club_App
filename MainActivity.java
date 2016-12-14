package com.spellflight.android.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private FetchMovies posterPath = new FetchMovies();
    private String dummyValue = "top_rated";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TESTING: NOT TESTED
        // DONE: 12/2/2016 add name of array that holds data
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MyApplication.getAppContext(), R.layout.activity_main, posterPath.getPosterPathArrayList());
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(arrayAdapter);


        startTask(arrayAdapter,dummyValue);
    }


    //TESTING: PASSED
    //checks network connection and starts background thread if connection is good
    public void startTask(ArrayAdapter arrayAdapter, String userPreference) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            posterPath.execute();
            // TODO: 12/2/2016 remove line below
            Log.d("Network is up", "startTask: ");
        } else {
            Toast toast = new Toast(MyApplication.getAppContext());
            toast.makeText(MyApplication.getAppContext(),R.string.network_check,Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    //TODO: 11/18/2016 ADD CODE THAT SAYS This product uses the TMDb API but is not endorsed or certified by TMDb. with their logo see this page https://www.themoviedb.org/about/logos-attribution



}
