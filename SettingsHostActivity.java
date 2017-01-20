package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Kim Kirk on 1/9/2017.
 */
public class SettingsHostActivity extends FragmentActivity {

private Fragment fragment;

    //TESTING: PASSED
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        //check to see if preference already has default value set, if not run this;

        //add Fragment to SettingHostActivity that shows Settings Menu
        //// DONE: 1/5/2017 this is not working, need to figure out how to add the fragment
        getFragmentManager().beginTransaction().add(R.id.container, new SettingsFragment()).commit();

        //if bundle has data in it replace current fragment with previous fragment instance
        //when fragment is recreated it will have previous instance's data
        if(savedInstanceState != null){
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "fragment");
            getSupportFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
        }

    }


    //TESTING: NOT TESTED
    //puts fragment instance into the bundle
    @Override
    protected void onSaveInstanceState(Bundle outState){
        getSupportFragmentManager().putFragment(outState,"fragment",fragment);
        super.onSaveInstanceState(outState);
    }
}


