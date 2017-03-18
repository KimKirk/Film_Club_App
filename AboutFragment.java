package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kim Kirk on 3/17/2017.
 */
public class AboutFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate the layout and use the View hierarchy from the host Activity to tell AndroidOS where to
        // put the Fragment's View in the host Activity's hierarchy
        return inflater.inflate(R.layout.about_fragment,container, false);
    }

}
