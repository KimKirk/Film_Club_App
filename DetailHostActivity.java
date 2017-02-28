package com.spellflight.android.popularmovies;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by Kim Kirk on 2/3/2017.
 */
public class DetailHostActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment_container);

        /*if(savedInstanceState != null) {
            //get the saved instance of MovieDetails Fragment

            //have data in bundle so replace fragment with savedinstancestate data for fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,MovieDetailsFragment saved instance goes here).commit();
        }*/

        //create new instace of fragment--you have to do this in order to add the Fragment to the Activity anyway
        //this new instance is created by AndroidOS going into the DetailFragment class and using the newInstance() method
       DetailFragment detailFragment = new DetailFragment();
        //set arguments for Fragment--this allows you to pass data into the newly created Fragment so it has the data you pass to it
        detailFragment.setArguments(getIntent().getExtras());

        //// DONE: 1/5/2017 this is not working, need to figure out how to add the fragment
        //add Fragment to DetailFragment
        getSupportFragmentManager().beginTransaction().add(R.id.detail_container,detailFragment).commit();
    }


    // TODO: 2/3/2017  save the instance of the Movie Details Fragment so that when the user changes orientation of the screen will get same data as before to make it seem as if nothing has changed, if so use code in SettingsHostActivity to save fragment instance and recreate it when onCreate() is called again
}
