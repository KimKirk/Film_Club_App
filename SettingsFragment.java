package com.spellflight.android.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Kim Kirk on 1/5/2017.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    //// TODO: 1/10/2017 check that modifiers on this are correct; need constant that is class accessible
    private static String PREF_KEY_MOVIE_SORT = "top-rated";
    String chosenPreference = "";

    //reference to listener persistent to avoid garbage collection
    SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged (SharedPreferences preferences, String key) {

            //check which Preference was changed by getting the key and if it matches
            //figure out which value was chosen
            //SKIP THIS FOR NOW//set the value chosen into the SharedPreferences object
            //use setEntryValues(CharSequence[]) where CharSequence[] is the name of the array that holds the values for the Preference (see arrays.xml resource)
            if(key.equals(R.string.shared_preference_key)) {

                //get value from SharedPreferences object
                //get your app's SharedPreference object using PreferenceManager.getDefaultSharedPreferences()
                //get the value from inside of the returned SharedPreference object by using one of the methods from the SharedPreferences class
                chosenPreference = preferences.getString(SettingsFragment.PREF_KEY_MOVIE_SORT, "");

                //send value into MainActivity's execute() method so it can pass value to change sort
                    //will need to create method that returns the string retrieved
                    //call this method in MainActivity, method will have to be class not instance so don't have to make new instance to use
                //update the summary for the Preference by using setSummary() on the Preference
                //if key doesn't match, do nothing
            }
        }
    };

    //add Preference to UI
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adds Preference to SettingsFragment when created
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume(){
        //registerOnSharedPreferenceChangeListener here
            //your listener is stored in mListener variable
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(mListener);

    }

    @Override
    public void onPause(){
        //unregisterOnSharedPreferenceChangeListener here
        //your listener is stored in mListener variable
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mListener);
    }

}
