package com.spellflight.android.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by Kim Kirk on 1/9/2017.
 */
public class SettingsHostActivity extends Activity {

    //TESTING: PASSED
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        PreferenceManager.setDefaultValues(this, R.xml.preferences,false);

        //add Fragment to SettingHostActivity that shows Settings Menu
        //// DONE: 1/5/2017 this is not working, need to figure out how to add the fragment
        getFragmentManager().beginTransaction().add(R.id.container, new SettingsFragment()).commit();

    }

}
