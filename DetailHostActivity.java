package com.spellflight.android.popularmovies;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by Kim Kirk on 2/3/2017.
 */
public class DetailHostActivity extends FragmentActivity {
    private DetailFragment detailFragment = new DetailFragment();

    // TODO: 3/1/2017 test this class to make sure the onCreate() runs correctly 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment_container);

        if(savedInstanceState != null) {
            //use newInstance() to recreate the new fragment with the correct data in it
            DetailFragment newDetailFragmentInstance = detailFragment.newInstance(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,newDetailFragmentInstance).commit();
        }
        else {
            //set arguments for Fragment--this allows you to pass data into the newly created Fragment so it has the data you pass to it, this is for the first time the fragment is created
            detailFragment.setArguments(getIntent().getExtras());
            //// DONE: 1/5/2017 this is not working, need to figure out how to add the fragment
            //add Fragment to DetailFragment
            getSupportFragmentManager().beginTransaction().add(R.id.detail_container, detailFragment).commit();
        }
    }

    // TODO: 2/3/2017  save the instance of the Movie Details Fragment so that when the user changes orientation of the screen will get same data as before to make it seem as if nothing has changed, if so use code in SettingsHostActivity to save fragment instance and recreate it when onCreate() is called again
}
