package com.spellflight.android.popularmovies;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Kim Kirk on 11/10/2016.
 */
public class ImageAdapterView extends BaseAdapter {
    //TODO: figure out why need to override getItemId() method


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //handling if there is no view object to put data into
        if (convertView == null) {
            convertView = View.inflate(MyApplication.getAppContext(), R.layout.activity_main, parent);
        }


        //position is used to get the current position of the data in the array/data structure...this returns an int so take the int and use arrayStructure[position] to extract data at that position in array

        //picasso will take each array data element and stick it into the view
        //should load() have the current position in the array as its input (so get position in array and return the value there) then add the base URL to that result?
        (ImageView) convertView = Picasso.with(MyApplication.getAppContext()).load(arrayAdapter[position]).into(convertView);

        return convertView;
    }

    //DONE: add Picasso library to gradle

    //DONE: - 11/16/2016 finish watching how to create custom arrayadapter in Google+ https://plus.google.com/events/chlh8qqr5q5grs1lajpqnvvql8k?authkey=CNXMrZuHsMWhNg

    // DONE: - 11/10/2016 follow along using this link for what to do next to add arrayAdapter stuff into layout https://developer.android.com/guide/topics/ui/layout/gridview.html
    // DONE:  - 11/18/2016 put data into arrayadapter for layout View to use need to use adapter to load images into the layout View, stick the Picasso code inside of the getView() method that you will override because Picasso creates the views for each of the data elements












}