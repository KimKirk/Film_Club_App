package com.spellflight.android.popularmovies;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


/**
 * Created by Kim Kirk on 2/3/2017.
 */
public class DetailHostActivity extends FragmentActivity {
    private DetailFragment detailFragment = new DetailFragment();
    private DetailFragment fragment;


    // DONE: 3/1/2017 test this class to make sure the onCreate() runs correctly 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment_container);

        if(savedInstanceState != null) {
            //recreates fragment instance
            fragment = (DetailFragment)getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
            //use newInstance() to recreate the new fragment with the correct data in it
            // TODO: 3/7/2017 figure out why this did not work correctly? fragment saved already had argument with data, so no need to create again in newInstance() 
            //DetailFragment newDetailFragmentInstance = fragment.newInstance(getIntent().getExtras());
            //replaces any current fragment with new fragment instance
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,fragment).commit();
        }
        else {
            //set arguments for Fragment--this allows you to pass data into the newly created Fragment so it has the data you pass to it, this is for the first time the fragment is created
            detailFragment.setArguments(getIntent().getExtras());
            //// DONE: 1/5/2017 this is not working, need to figure out how to add the fragment
            //add Fragment to DetailFragment
            getSupportFragmentManager().beginTransaction().add(R.id.detail_container, detailFragment).commit();
        }
    }

    // DONE: 2/3/2017  save the instance of the Movie Details Fragment so that when the user changes orientation of the screen will get same fragment instance as before to make it seem as if nothing has changed, if so use code in SettingsHostActivity to save fragment instance and recreate it when onCreate() is called again
    //// DONE: 3/6/2017 this does not save the fragment, fragment is not put into bundle
    // FIXME: 3/7/2017 fragment is not being saved during second device rotation because there is no fragment in the fragment manager?
    @Override
    protected void onSaveInstanceState(Bundle outState){
        Log.d("fragmentManagerState", "onSaveInstanceState: " + getSupportFragmentManager().getFragments());
        //saves fragment instance before fragment is killed
        getSupportFragmentManager().putFragment(outState,"fragment",detailFragment);
        super.onSaveInstanceState(outState);
    }
}
