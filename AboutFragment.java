package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kim Kirk on 3/17/2017.
 */

/*
 * This call creates new About Fragment that gives information about the app
 */


public class AboutFragment extends Fragment {

    //inflate the layout for About Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        // put the Fragment's View in the host Activity's hierarchy
        return inflater.inflate(R.layout.about_fragment,container, false);
    }

}
