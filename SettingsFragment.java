package com.spellflight.android.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Kim Kirk on 1/5/2017.
 */
public class SettingsFragment extends PreferenceFragment {

    //// TODO: 1/15/2017 REFACTOR CODE: SEE BELOW
        //// TODO: 1/15/2017 look for redundant code and create a method to house it instead of having it in multiple areas inside class
        //// TODO: 1/15/2017 replace "movie sort" with string

    // DONE: 1/11/2017 this does not work, causes crash because both methods are called too many times, figure out how to create listener object that persists in memory and write method body for onSharedPreferenceChange
    //reference to listener persistent to avoid garbage collection
    private SharedPreferences.OnSharedPreferenceChangeListener mListener;
    private Preference preference;
    private CharSequence summary;
    private String prefKey = "preference summary";
    private String retrievedSummary;


    //TESTING: PASSED
    //add Preference to UI
    //set summary for preference that is same as sharedpreference value
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adds Preference to SettingsFragment when created
        addPreferencesFromResource(R.xml.preferences);
        //have to set this for the fragment even though the default value for sharedpreferences is already set in mainactivity...the fragment has to know that its preference object will get the default value
        PreferenceManager.setDefaultValues(getActivity(),R.xml.preferences,false);
        preference = getPreferenceManager().findPreference("movie sort");
        preference.setSummary(getPreferenceManager().getSharedPreferences().getString("movie sort",""));



    }

    //must put restore inside this method because activity created means fragment instance gets restored, then can get preference when fragment instance created
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //get data from bundle
            retrievedSummary = savedInstanceState.getString(prefKey);
            //set summary to preference
            preference = getPreferenceManager().findPreference("movie sort");
            preference.setSummary(retrievedSummary);
        }
    }



    //// DONE: 1/12/2017 this does not work, it runs listener but does not run onSharedPreferencesChanged()
    @Override
    public void onResume(){
        super.onResume();
        //registerOnSharedPreferenceChangeListener here
            //your listener is stored in mListener variable
         if(mListener == null) {
             mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                 public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                     //check which Preference was changed by getting the key and if it matches
                     //figure out which value was chosen
                     //key tells me which SharedPreference changed, value = value it was changed to
                     Preference preference = getPreferenceManager().findPreference(key);
                     if (key.equals("movie sort")) {
                         //UPDATE SUMMARY TO SHARED PREFERENCES VALUE
                         //get preference object
                         //set summary on preference object to key from sharedpreferences object
                         preference.setSummary(preferences.getString(key,""));

                     }
                 }
             };
         }
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(mListener);
    }


    //TESTING: PASSED
    @Override
    public void onPause(){
        super.onPause();
        //unregisterOnSharedPreferenceChangeListener here
        //your listener is stored in mListener variable
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        //get data from preference summary
        //get preference
        preference = getPreferenceManager().findPreference("movie sort");
        //get summary
        summary = preference.getSummary();
        //save it into Bundle
        outState.putCharSequence(prefKey, summary);
        super.onSaveInstanceState(outState);
    }
}


