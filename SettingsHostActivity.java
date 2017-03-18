package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Kim Kirk on 1/9/2017.
 */

/*
 * This class hosts the Settings Fragment
 */

public class SettingsHostActivity extends FragmentActivity {


    //holds a newly created SettingsFragment instance
    private SettingsFragment mSettingsFragment = new SettingsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the content view container for the Fragment inside of the host Activity's view hierarchy
        setContentView(R.layout.fragment_container);

        //if bundle has data in it replace current fragment with saved fragment instance
        if(savedInstanceState != null){
            //get the Fragment stored inside the Bundle of savedInstanceState
            android.app.Fragment fragment = getFragmentManager().getFragment(savedInstanceState,"fragment");

            //start transaction to replace any current Fragment with saved fragment
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
        //if the Bundle sent into onCreate method is empty then just attach the Settings Fragment to the host Activity
        else {
            //start transaction to add new fragment
            getFragmentManager().beginTransaction().add(R.id.container, mSettingsFragment).commit();
        }

    }


    //saves the fragment instance
    @Override
    protected void onSaveInstanceState(Bundle outState){
        //gets the fragment manager, puts current fragment instance that is found by the fragment manager into the Bundle
        getFragmentManager().putFragment(outState,"fragment",getFragmentManager().findFragmentById(R.id.container));

        //saves the Bundle
        super.onSaveInstanceState(outState);
    }
}


