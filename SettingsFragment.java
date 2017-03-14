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
    //holds preference returned from Preference Manager
    private Preference preference;
    //holds data retrieved from Preference
    private CharSequence summary;
    //holds key used to retrieve Preference data from Bundle
    private String prefSummaryKey = "preference summary";
    //holds summary data retrieved from Preference used to set summary for Preference
    private String retrievedSummary;
    //holds key for Preference
    private String prefKey = "movie sort";


    //add Preference to UI
    //set summary for preference that is same as sharedpreference value
    //TESTING: PASSED
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adds Preference to SettingsFragment when created
        addPreferencesFromResource(R.xml.preferences);
        //have to set this for the fragment even though the default value for sharedpreferences is already set in mainactivity...the fragment has to know that its preference object will get the default value
        PreferenceManager.setDefaultValues(getActivity(),R.xml.preferences,false);
        //use the Preference key to get the Preference from the Preference Manager
        preference = getPreferenceManager().findPreference(prefKey);
        //set the summary for the Preference by getting the string value in the SharedPreferences file
        preference.setSummary(getPreferenceManager().getSharedPreferences().getString(prefKey,""));

    }


    //must put retrieve Bundle data inside this method because Activity created means can get access to Preference data and Fragment instance needs access to Preference data, so wait until Activity is fully created then can get Preference restored Fragment instance
    //gets data from Bundle and sets summary for Preference
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            //get data from bundle
            retrievedSummary = savedInstanceState.getString(prefSummaryKey);
            //use PreferenceManager to find Preference using key
            preference = getPreferenceManager().findPreference(prefKey);
            //set summary to preference
            preference.setSummary(retrievedSummary);
        }
    }



    //creates a new listener as an anonymous inner class and sets it onto the SharedPreferences so that when the when the host Activity resumes so does the Fragment and the listener can be attached
    //// DONE: 1/12/2017 this does not work, it runs listener but does not run onSharedPreferencesChanged()
    @Override
    public void onResume(){
        super.onResume();
        //registerOnSharedPreferenceChangeListener here
            //your listener is stored in mListener variable
         if(mListener == null) {
             //creates new listener for the SharedPreferences
             mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                 //instantiates in place the methods for this anonymous inner class
                 public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                     //check which Preference was changed by getting the key and if it matches
                     //figure out which value was chosen
                     //key tells me which SharedPreference changed, value = value it was changed to
                     Preference preference = getPreferenceManager().findPreference(key);
                     if (key.equals(prefKey)) {
                         //UPDATE SUMMARY TO SHARED PREFERENCES VALUE
                         //get preference object
                         //set summary on preference object to key from sharedpreferences object
                         preference.setSummary(preferences.getString(key,""));
                     }
                 }
             };
         }
        //registers the listener on the SharedPreference so if changes are made to SharedPreference file you will know about it
        //changes made to the SharedPreference file means changes made to Preference object because SharedPreference file shows updates to Preference object
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(mListener);
    }


    //unregister onSharedPreferenceChangeListener
    //TESTING: PASSED
    @Override
    public void onPause(){
        super.onPause();
        //unregister OnSharedPreferenceChangeListener here because this is where you need to release objects that use up memory in case Fragment is destroyed, there is memory freed for other resources on device
        //your listener is stored in mListener variable
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mListener);
    }

    //you want to save the Preference object's summary data so can be used when recreate Preference object after destroyed
    //save Preference data
    @Override
    public void onSaveInstanceState (Bundle outState){
        //get data from preference summary and save it to Bundle
        //get preference
        preference = getPreferenceManager().findPreference(prefKey);
        //get summary
        summary = preference.getSummary();
        //put it into Bundle
        outState.putCharSequence(prefSummaryKey, summary);
        //save the Bundle
        super.onSaveInstanceState(outState);
    }
}


