package com.spellflight.android.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Kim Kirk on 1/5/2017.
 */

/*
 * This class creates the Preference Fragment for the Settings Menu
 */

public class SettingsFragment extends PreferenceFragment {

    //reference to listener persistent to avoid garbage collection
    private SharedPreferences.OnSharedPreferenceChangeListener mListener;

    //holds mPreference returned from Preference Manager
    private Preference mPreference;

    //holds key used to retrieve Preference data from Bundle
    private String mPrefSummaryKey = "mPreference mSummary";

    //holds key for Preference
    private String mPrefKey = "movie sort";



    //adds Preference to Settings Menu
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adds Preference to SettingsFragment when created
        addPreferencesFromResource(R.xml.preferences);

        //set default value for preference
        PreferenceManager.setDefaultValues(getActivity(),R.xml.preferences,false);

        //use the Preference key to get the Preference object from the Preference Manager
        mPreference = getPreferenceManager().findPreference(mPrefKey);

        //set the summary for the Preference object by getting the string value in the SharedPreferences file
        mPreference.setSummary(getPreferenceManager().getSharedPreferences().getString(mPrefKey,""));

    }


    //gets data from Bundle and sets summary for Preference
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //get data from bundle
            String retrievedSummary = savedInstanceState.getString(mPrefSummaryKey);

            //use PreferenceManager to find Preference using key
            mPreference = getPreferenceManager().findPreference(mPrefKey);

            //set mSummary to mPreference
            mPreference.setSummary(retrievedSummary);
        }
    }



    //creates a new listener as an anonymous inner class and sets it onto the SharedPreferences
    @Override
    public void onResume(){
        super.onResume();

        //registerOnSharedPreferenceChangeListener
         if(mListener == null) {
             //creates new listener for the SharedPreferences file

             mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                 //implements onSharedPreferenceChanged method
                 public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {

                     //get the preference value using the key
                     Preference preference = getPreferenceManager().findPreference(key);
                     if (key.equals(mPrefKey)) {

                         //set summary on preference to key from sharedpreferences
                         preference.setSummary(preferences.getString(key,""));
                     }
                 }
             };
         }
        //registers the listener on the SharedPreference so if changes are made to SharedPreference file you will know about it
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(mListener);
    }


    //unregister onSharedPreferenceChangeListener
    @Override
    public void onPause(){
        super.onPause();
        //unregister OnSharedPreferenceChangeListener here
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mListener);
    }


    //save Preference data
    @Override
    public void onSaveInstanceState (Bundle outState){
        //get data from Preference Summary
        mPreference = getPreferenceManager().findPreference(mPrefKey);

        //holds data retrieved from Preference
        CharSequence summary = mPreference.getSummary();

        //put summary data into Bundle
        outState.putCharSequence(mPrefSummaryKey, summary);

        //save the Bundle
        super.onSaveInstanceState(outState);
    }
}


