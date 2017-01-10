package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Kim Kirk on 1/5/2017.
 */
public class SettingsFragment extends PreferenceFragment{

    //add Preference to UI
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
