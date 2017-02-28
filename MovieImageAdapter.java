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

    private String imageURL;


    //constructor to create new MovieImageAdapter to be used to to create a new array adapter
    public MovieImageAdapter(Context context, int resource, ArrayList<MovieDetails> objects) {
        //we only want to use the parent constructor, we create the constructor for the MovieImageAdapter subclass so that we can create a new object of the subclass type and so we can pass the values to the superclass constructor and it can use the values as needed to construct a new array adapter
        super(context, resource, objects);


    }


    //called by AndroidOS only, the body of the method is where you take the paramters and use them to create the view that the arrayadapter sends into the layout and shows the view on screen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        //handling if there is no view object to put data into
        if (convertView == null) {
            imageView = new ImageView(getContext());
        }
        else {
            imageView = (ImageView) convertView;
        }

        //position is used to get the current position of the data in the array/data structure...this returns an int so take the int and use arrayStructure[position] to extract data at that position in array

        // DONE: 12/18/2016 figure out if you need to turn into a string because value is already a String when check via debugging
        //gets item in arrayadapter at the specified position
        MovieDetails imageObject = (MovieDetails)getItem(position);
        // DONE: 1/25/2017  go into object and retrieve just the "image" string

        imageURL = imageObject.img;

        // DONE: 12/18/2016 figure out if you need to define this outside the method but inside the class
        //holds base URL to be added as prefix to string URL suffix from arrayadapter
        String baseURL = "https://image.tmdb.org/t/p/w500";
                //need to use arraylist that sent into adapter and position value to get string in arraylist at that position
                    //e.g. posterPath[position] see this note in evernote: Using Picasso with ArrayAdapter


        //picasso will take each array data element and stick it into the view
        //should load() have the current position in the array as its input (so get position in array and return the value there) then add the base URL to that result?
        //make sure you are getting the arraylist that you sent into the adapter, don't create a new instance of the fetchmovies class or will get totally new arraylist in wrong position
        Picasso.with(getContext()).load(baseURL + "/" + imageURL).into(imageView);

        return imageView;

    }

    //DONE: add Picasso library to gradle
    //DONE: - 11/16/2016 finish watching how to create custom arrayadapter in Google+ https://plus.google.com/events/chlh8qqr5q5grs1lajpqnvvql8k?authkey=CNXMrZuHsMWhNg
    // DONE: - 11/10/2016 follow along using this link for what to do next to add arrayAdapter stuff into layout https://developer.android.com/guide/topics/ui/layout/gridview.html
    // DONE:  - 11/18/2016 put data into arrayadapter for layout View to use need to use adapter to load images into the layout View, stick the Picasso code inside of the getView() method that you will override because Picasso creates the views for each of the data elements

}
