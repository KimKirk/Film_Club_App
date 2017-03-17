package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Kim Kirk on 3/17/2017.
 */
public class AboutHostActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adds space in host Activity View Hierarchy for Fragment container
        setContentView(R.layout.about_fragment_container);

        //recreates mFragment instance
        if (savedInstanceState != null) {
            //gets Fragment from Bundle
            // TODO: 3/17/2017 this placeholder will correct when create About Fragment class
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, "mFragment");
            //replaces any current mFragment with new mFragment instance
            getSupportFragmentManager().beginTransaction().replace(R.id.about_container, fragment).commit();
        }

        //if Bundle is empty then create new Fragment
        else {
            //create new About Fragment
            AboutFragment aboutFragment = new AboutFragment();
            //add Fragment to host Activity
            // TODO: 3/17/2017 this will correct when create About Fragment class
            getSupportFragmentManager().beginTransaction().add(R.id.about_container, aboutFragment).commit();
        }
    }


    //saves mFragment instance before mFragment is killed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //saves mFragment instance before mFragment is killed
        //get the current Fragment instance from the SupportFragmentManager and put into Bundle
        getSupportFragmentManager().putFragment(outState, "mFragment", getSupportFragmentManager().findFragmentById(R.id.about_container));
        //save the Bundle
        super.onSaveInstanceState(outState);
    }
}

