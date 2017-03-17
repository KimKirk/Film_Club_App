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

    //// DONE: 1/15/2017 REFACTOR CODE: SEE BELOW
        //// DONE: 1/15/2017 look for redundant code and create a method to house it instead of having it in multiple areas inside class
        //// DONE: 1/15/2017 replace "movie sort" with string
    // DONE: 1/11/2017 this does not work, causes crash because both methods are called too many times, figure out how to create listener object that persists in memory and write method body for onSharedPreferenceChange
    //reference to listener persistent to avoid garbage collection
    private SharedPreferences.OnSharedPreferenceChangeListener mListener;

    //holds mPreference returned from Preference Manager
    private Preference mPreference;

    //holds data retrieved from Preference
    private CharSequence mSummary;

    //holds key used to retrieve Preference data from Bundle
    private String mPrefSummaryKey = "mPreference mSummary";

    //holds mSummary data retrieved from Preference used to set mSummary for Preference
    private String mRetrievedSummary;

    //holds key for Preference
    private String mPrefKey = "movie sort";



    //add Preference to UI
    //set mSummary for mPreference that is same as sharedpreference value
    //TESTING: PASSED
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adds Preference to SettingsFragment when created
        addPreferencesFromResource(R.xml.preferences);

        //have to set this for the fragment even though the default value for sharedpreferences is
        // already set in mainactivity...the fragment has to know that its mPreference object will get the default value
        PreferenceManager.setDefaultValues(getActivity(),R.xml.preferences,false);

        //use the Preference key to get the Preference object from the Preference Manager
        mPreference = getPreferenceManager().findPreference(mPrefKey);

        //set the mSummary for the Preference object by getting the string value in the SharedPreferences file
        mPreference.setSummary(getPreferenceManager().getSharedPreferences().getString(mPrefKey,""));

    }


    //must put retrieve Bundle data inside this method because Activity created means can
    // get access to Preference data and Fragment instance needs access to Preference data, so
    // wait until Activity is fully created then can get Preference restored Fragment instance
    //gets data from Bundle and sets mSummary for Preference
    //TESTING: PASSED
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //get data from bundle
            mRetrievedSummary = savedInstanceState.getString(mPrefSummaryKey);

            //use PreferenceManager to find Preference using key
            mPreference = getPreferenceManager().findPreference(mPrefKey);

            //set mSummary to mPreference
            mPreference.setSummary(mRetrievedSummary);
        }
    }



    //creates a new listener as an anonymous inner class and sets it onto the SharedPreferences so
    // that when the when the host Activity resumes so does the Fragment and the listener can be attached
    //// DONE: 1/12/2017 this does not work, it runs listener but does not run onSharedPreferencesChanged()
    //TESTING: PASSED
    @Override
    public void onResume(){
        super.onResume();

        //registerOnSharedPreferenceChangeListener here
        //your listener is stored in mListener variable
         if(mListener == null) {
             //creates new listener for the SharedPreferences file
             mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                 //instantiates in place the methods for this anonymous inner class
                 public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                     //check which Preference was changed by getting the key and if it matches
                     //figure out which value was chosen
                     //key tells me which SharedPreference changed, value = value it was changed to
                     Preference preference = getPreferenceManager().findPreference(key);
                     if (key.equals(mPrefKey)) {
                         //UPDATE SUMMARY TO SHARED PREFERENCES VALUE
                         //get mPreference object
                         //set mSummary on mPreference object to key from sharedpreferences object
                         preference.setSummary(preferences.getString(key,""));
                     }
                 }
             };
         }
        //registers the listener on the SharedPreference so if changes are made to SharedPreference file you will know about it
        //changes made to the SharedPreference file means changes made to Preference object because
        // SharedPreference file shows updates to Preference object
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

    //you want to save the Preference object's mSummary data so can be used when recreate Preference object after destroyed
    //save Preference data
    //TESTING: PASSED
    @Override
    public void onSaveInstanceState (Bundle outState){
        //get data from mPreference mSummary and save it to Bundle
        //get mPreference
        mPreference = getPreferenceManager().findPreference(mPrefKey);

        //get mSummary
        mSummary = mPreference.getSummary();

        //put it into Bundle
        outState.putCharSequence(mPrefSummaryKey, mSummary);

        //save the Bundle
        super.onSaveInstanceState(outState);
    }
}


