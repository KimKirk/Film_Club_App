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

/*
 * This class creates custom array adapter
 */
public class MovieImageAdapter extends ArrayAdapter {

    //constructor to create new MovieImageAdapter used to create a new array adapter that holds MovieDetails objects
    public MovieImageAdapter(Context context, int resource, ArrayList<MovieDetails> objects) {
        super(context, resource, objects);

    }


    // create the view that the arrayadapter sends into the layout and shows the view on screen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //holds new ImageView object
        ImageView imageView;

        //create a new ImageView if convertView is empty
        if (convertView == null) {
            imageView = new ImageView(getContext());
        }
        //convert the View that comes into this method into an ImageView so it can hold the image properly
        else {
            imageView = (ImageView) convertView;
        }

        //gets item in arrayadapter at the specified position
        MovieDetails imageObject = (MovieDetails)getItem(position);

        //holds the string that has partial URL for image
        String imageUrl = imageObject.mImage;

        //holds base URL to be added as prefix to partial URL
        String baseURL = "https://image.tmdb.org/t/p/w500";

        //load() gets the data, into() gets the View object to stick data into
        Picasso.with(getContext()).load(baseURL + "/" + imageUrl).placeholder(R.drawable.filmplaceholder).error(R.drawable.picassoimageerror).into(imageView);


        //return the View back to the caller will be used to put on screen for UI
        return imageView;
    }
}
