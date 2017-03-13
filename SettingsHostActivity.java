package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Kim Kirk on 1/9/2017.
 */
public class SettingsHostActivity extends FragmentActivity {

    private android.app.Fragment mFragment;
    //holds a newly created SettingsFragment instance
    private SettingsFragment mSettingsFragment = new SettingsFragment();


    //TESTING: PASSED
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        //if bundle has data in it replace current mFragment with previous mFragment instance
        //when mFragment is recreated it will have previous instance's data
        if(savedInstanceState != null){
            mFragment = getFragmentManager().getFragment(savedInstanceState,"mFragment");
            getFragmentManager().beginTransaction().replace(R.id.container , mFragment).commit();
        }
        else {
            //// DONE: 1/5/2017 this is not working, need to figure out how to add the mFragment
            //add Fragment to SettingHostActivity that shows Settings Menu
            //use getFragmentManager because you are using a Preference Fragment via SettingsFragment in add()
            getFragmentManager().beginTransaction().add(R.id.container, mSettingsFragment).commit();
        }

    }


    //TESTING: PASSED
    //puts mFragment instance into the bundle, saves the mFragment instance so that when it needs to be recreated host activity can retrieve saved mFragment instance
    @Override
    protected void onSaveInstanceState(Bundle outState){
        //gets the support fragment manager, puts current fragment instance that is found by the support fragment manager into the Bundle "outstate"
        getFragmentManager().putFragment(outState,"mFragment",getFragmentManager().findFragmentById(R.id.container));
        //saves Bundle
        super.onSaveInstanceState(outState);
    }
}


