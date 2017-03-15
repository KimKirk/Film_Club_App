package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Kim Kirk on 2/6/2017.
 */
public class DetailFragment extends Fragment{

    // DONE: 3/1/2017 test this method to make sure it works correctly

    @Override
    public void onCreate(Bundle savedInstanceState){
        //Fragment instance gets recreated in host Activity with Fragment arguments, so don't need to take saved data and set Views here because the data is set inside Fragment instance with arguments each time Fragment is created
        super.onCreate(savedInstanceState);
    }

    //DESIGN: possibly override and implement onActivityCreated() where you will set the text for the Fragment's View using the data you received from Intent; do this in this method because need to make sure the View has been initialized for the Fragment before adding text to it
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        //use basic functionality from superclass method
        super.onActivityCreated(savedInstanceState);

        // DONE: 2/14/2017  get the data from the new instance of Detail Fragment
        // DONE: 2/14/2017  set the data on the views that will show the data
        //find Views by ID for all TextViews
        TextView titleView = (TextView)getActivity().findViewById(R.id.original_title);
        TextView overvwView = (TextView)getActivity().findViewById(R.id.overview);
        TextView releaseDateView = (TextView)getActivity().findViewById(R.id.release_date);
        TextView voteView = (TextView)getActivity().findViewById(R.id.vote_average);
        //get the arguments from the Fragment and get the Parcelable object with the given key
        MovieDetailsParcel movieDetailsParcel = getArguments().getParcelable("movieDetails");
        //get field values from inside parcelable object using getters from the Parcelable class you created
        titleView.setText(movieDetailsParcel.getmTitle());
        overvwView.setText(movieDetailsParcel.getmOverview());
        releaseDateView.setText("Release Date: " + movieDetailsParcel.getmRelease());
        voteView.setText("Average Rating: " + movieDetailsParcel.getmVote()+ " /10");

    }


    //don't need to save text for TextViews because already setting Fragment's arguments each time fragment gets created and those arguments are set inside the Fragment then just use getters from MovieDetailsParcel class  to set text views
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate the layout and use the View hierarchy from the host Activity to tell AndroidOS where to put the Fragment's View in the host Activity's hierarchy
        return inflater.inflate(R.layout.detail_fragment,container, false);
    }


}
