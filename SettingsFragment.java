package com.spellflight.android.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by Kim Kirk on 1/5/2017.
 */
public class SettingsFragment extends PreferenceFragment {

    //// // FIXME: 1/11/2017 this does not work, causes crash because both methods are called too many times, figure out how to create listener object that persists in memory and write method body for onSharedPreferenceChange
    //reference to listener persistent to avoid garbage collection
    SharedPreferences.OnSharedPreferenceChangeListener mListener;


    //TESTING: PASSED
    //add Preference to UI
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adds Preference to SettingsFragment when created
        addPreferencesFromResource(R.xml.preferences);
    }


    //// FIXME: 1/12/2017 this does not work, it runs listener but does not run onSharedPreferencesChanged()
    @Override
    public void onResume(){
        super.onResume();
        //registerOnSharedPreferenceChangeListener here
            //your listener is stored in mListener variable
         if(mListener == null) {
             new SharedPreferences.OnSharedPreferenceChangeListener() {
                 public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
                     //check which Preference was changed by getting the key and if it matches
                     //figure out which value was chosen
                     Preference preference = findPreference(key);
                     //SKIP THIS FOR NOW//set the value chosen into the SharedPreferences object
                     //use setEntryValues(CharSequence[]) where CharSequence[] is the name of the array that holds the values for the Preference (see arrays.xml resource)
                     if (key.equals(R.string.list_preference_key)) {
                         //UPDATE SUMMARY TO SHARED PREFERENCES VALUE
                         preference.setSummary(((ListPreference) preference).getEntry());
                     }
                 }
             };
         }
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(mListener);
    }


    @Override
    public void onPause(){
        super.onPause();
        //unregisterOnSharedPreferenceChangeListener here
        //your listener is stored in mListener variable
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mListener);
    }
}
