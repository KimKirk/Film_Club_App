package com.spellflight.android.popularmovies;


import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Kim Kirk on 11/10/2016.
 */
public class ImageAdapterView extends BaseAdapter {


    //holds arrayadapter
    ArrayAdapter<ImageView> arrayAdapter = new ArrayAdapter<ImageView>(MyApplication.getAppContext(),android.R.layout.simple_gallery_item, imageArray);

    // TODO: 11/10/2016 create array that holds image data from moviedb server, hold in a variable, replace imageArray above with variable name 
    // TODO: 11/10/2016 follow along using this link for what to do next https://developer.android.com/guide/topics/ui/layout/gridview.html










}
