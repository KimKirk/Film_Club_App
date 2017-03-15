package com.spellflight.android.popularmovies;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by Kim Kirk on 2/3/2017.
 */
public class DetailHostActivity extends FragmentActivity {
    private DetailFragment mDetailFragment = new DetailFragment();
    private DetailFragment mFragment;


    // DONE: 3/1/2017 test this class to make sure the onCreate() runs correctly 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment_container);

        if(savedInstanceState != null) {
            //recreates mFragment instance
            mFragment = (DetailFragment)getSupportFragmentManager().getFragment(savedInstanceState,"mFragment");
            //use newInstance() to recreate the new mFragment with the correct data in it
            // DONE: 3/7/2017 mFragment saved already had argument with data, so no need to create again in newInstance()
            //DetailFragment newDetailFragmentInstance = mFragment.newInstance(getIntent().getExtras());
            //replaces any current mFragment with new mFragment instance
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, mFragment).commit();
        }
        else {
            //set arguments for Fragment--this allows you to pass data into the newly created Fragment so it has the data you pass to it, this is for the first time the mFragment is created
            mDetailFragment.setArguments(getIntent().getExtras());
            //// DONE: 1/5/2017 this is not working, need to figure out how to add the mFragment
            //add Fragment to DetailFragment
            getSupportFragmentManager().beginTransaction().add(R.id.detail_container, mDetailFragment).commit();
        }
    }

    // DONE: 2/3/2017  save the instance of the Movie Details Fragment so that when the user changes orientation of the screen will get same mFragment instance as before to make it seem as if nothing has changed, if so use code in SettingsHostActivity to save mFragment instance and recreate it when onCreate() is called again
    //// DONE: 3/6/2017 this does not save the mFragment, mFragment is not put into bundle
    // DONE: 3/7/2017 mFragment is not being saved during second device rotation because there is no mFragment in the mFragment manager?
    @Override
    protected void onSaveInstanceState(Bundle outState){

        //saves mFragment instance before mFragment is killed
        getSupportFragmentManager().putFragment(outState,"mFragment",getSupportFragmentManager().findFragmentById(R.id.detail_container));
        super.onSaveInstanceState(outState);
    }
}
