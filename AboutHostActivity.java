package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Kim Kirk on 3/17/2017.
 */

/*
 *  This class hosts the About Fragment
 */

public class AboutHostActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adds space in host Activity View Hierarchy for fragment container
        setContentView(R.layout.about_fragment_container);

        //recreates fragment instance
        if (savedInstanceState != null) {
            //gets fragment from Bundle
            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");

            //replaces any current fragment with saved fragment instance
            getSupportFragmentManager().beginTransaction().replace(R.id.about_container, fragment).commit();
        }
        //if Bundle is empty then create new fragment
        else {
            //create new About Fragment
            AboutFragment aboutFragment = new AboutFragment();

            //add Fragment to host Activity
            getSupportFragmentManager().beginTransaction().add(R.id.about_container, aboutFragment).commit();
        }
    }


    //saves fragment instance before fragment is destroyed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //get the current Fragment instance from the SupportFragmentManager and put into Bundle
        getSupportFragmentManager().putFragment(outState, "fragment", getSupportFragmentManager().findFragmentById(R.id.about_container));

        //save the Bundle
        super.onSaveInstanceState(outState);
    }
}

