package com.spellflight.android.popularmovies;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kim Kirk on 11/10/2016.
 */
public class MovieImageAdapter extends ArrayAdapter {
    //DONE: figure out why need to override getItemId() method

    //holds the string that has partial URL for image
    private String mImageUrl;

    //constructor to create new MovieImageAdapter to be used to create a new array adapter that holds only MovieDetails objects
    //we create the constructor for the MovieImageAdapter class so that we can create a new object of the class type and so we can pass the values to the superclass constructor and it can use the values as needed to construct a new array adapter
    //this is considered a custom array adapter because it holds custom object which is MovieDetails and standard array adapter won't let you hold custom objects in it only String or Numbers
    public MovieImageAdapter(Context context, int resource, ArrayList<MovieDetails> objects) {
        //we only want to use the parent constructor to pass the values from MovieImageAdapter into so the method from the super class can do what it needs to do to create a new array adapter when given custom adapter input
        super(context, resource, objects);

    }


    //called by AndroidOS only, the body of the method is where you take the parameters and use them to create the view that the arrayadapter sends into the layout and shows the view on screen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //position is the current position in the arrayadapter, returns an integer that represents the index position in array
            //first time position will be at 0 in array, each time after the position will advance by 1 as it moves through array
        //convertView is the View object that will be used to put new View into as arrayadapter creates new View to put on screen
            //first time convertView is used it is null, each time after that convertView has a View already inside of it so you just use that convertView again to put a different View inside of it (this is how the recycling works)
        //

        //holds the value returned after create new ImageView object (because the Views that will show on screen are images, so need to save them into ImageView views)
        ImageView imageView;

        //handling if convertView is empty then need to put a new View object into it
        if (convertView == null) {
            //create a new ImageView using the ImageView constructor
            imageView = new ImageView(getContext());
        }
        else {
            //convert the View that comes into this method into an ImageView so it can hold the image properly
            imageView = (ImageView) convertView;
        }


        // DONE: 12/18/2016 figure out if you need to turn into a string because value is already a String when check via debugging
        //gets item in arrayadapter at the specified position
        MovieDetails imageObject = (MovieDetails)getItem(position);
        // DONE: 1/25/2017  go into object and retrieve just the "image" string
        mImageUrl = imageObject.img;

        // DONE: 12/18/2016 figure out if you need to define this outside the method but inside the class
        //holds base URL to be added as prefix to string URL suffix from arrayadapter
        String baseURL = "https://image.tmdb.org/t/p/w500";

        //picasso will take each array data element and stick it into the View
        //load() gets the data, into() gets the View object to stick data into
        Picasso.with(getContext()).load(baseURL + "/" + mImageUrl).into(imageView);

        //return the View back to the caller of this method because the View is what will be used to put on screen for UI
        return imageView;
    }

    //DONE: add Picasso library to gradle
    //DONE: - 11/16/2016 finish watching how to create custom arrayadapter in Google+ https://plus.google.com/events/chlh8qqr5q5grs1lajpqnvvql8k?authkey=CNXMrZuHsMWhNg
    // DONE: - 11/10/2016 follow along using this link for what to do next to add arrayAdapter stuff into layout https://developer.android.com/guide/topics/ui/layout/gridview.html
    // DONE:  - 11/18/2016 put data into arrayadapter for layout View to use need to use adapter to load images into the layout View, stick the Picasso code inside of the getView() method that you will override because Picasso creates the views for each of the data elements

}
