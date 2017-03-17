package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Kim Kirk on 1/9/2017.
 */
public class SettingsHostActivity extends FragmentActivity {

    //holds the returned value of FragmentManager
    private android.app.Fragment mFragment;
    //holds a newly created SettingsFragment instance
    private SettingsFragment mSettingsFragment = new SettingsFragment();


    //sets the content view of the Fragment inside of the host Activity's view hierarchy
    //TESTING: PASSED
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //gets the saved instance View data inside the Bundle and does default creation of setting saved View data onto that View
        super.onCreate(savedInstanceState);

        //sets the content view container for the Fragment inside of the host Activity's view hierarchy
        setContentView(R.layout.fragment_container);

        //if bundle has data in it replace current mFragment with previous mFragment instance
        //when mFragment is recreated it will have previous instance's data
        if(savedInstanceState != null){
            //get the Fragment stored inside the Bundle of savedInstanceState
            //start transaction to replace any current Fragment with Fragment inside of mFragment
            mFragment = getFragmentManager().getFragment(savedInstanceState,"mFragment");
            getFragmentManager().beginTransaction().replace(R.id.container , mFragment).commit();
        }

        //if the Bundle sent into onCreate method is empty then just attach the Settings Fragment to the host Activity
        else {//// DONE: 1/5/2017 this is not working, need to figure out how to add the mFragment
            //add Fragment to SettingHostActivity that shows Settings Menu
            //use getFragmentManager because you are using a Preference Fragment via SettingsFragment in
            // add(), Preference Fragment does not support supportFragmentManager because Preference Fragment created before
            // Fragments existed in Android
            getFragmentManager().beginTransaction().add(R.id.container, mSettingsFragment).commit();
        }

    }


    //TESTING: PASSED
    //puts mFragment instance into the bundle, saves the mFragment instance so that when it needs to be
    // recreated host activity can retrieve saved mFragment instance
    @Override
    protected void onSaveInstanceState(Bundle outState){
        //gets the fragment manager, puts current fragment instance that is found by the fragment manager into the Bundle "outstate"
        //saves Bundle
        getFragmentManager().putFragment(outState,"mFragment",getFragmentManager().findFragmentById(R.id.container));
        super.onSaveInstanceState(outState);
    }
}


