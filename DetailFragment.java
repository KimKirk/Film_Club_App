package com.spellflight.android.popularmovies;

import android.os.Bundle;
import android.os.Parcelable;
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
    // TODO: 3/6/2017 figure out how this method can be used inside DetailHostActivity in the future so that fragment field setting is independent of activity that host's it
    //this is a way to set the Fragment's fields so that you don't use the default constructor and then have to call setters
    public static DetailFragment newInstance(Parcelable movDetParcel){
        //create new instance of detail fragment
        DetailFragment detailFrag = new DetailFragment();
        //create new instance of a Bundle that holds data
        Bundle arguments = new Bundle();
        //put index value received into the Bundle and give it a key
        arguments.putParcelable("movieDetailsParcel",movDetParcel);
        //set arguments/field for the Detail Fragment
        detailFrag.setArguments(arguments);
        //outer bundle = arguments, bundle, parcelable
        //return the new instance with the set field to the caller
        return detailFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    //DESIGN: possibly override and implement onActivityCreated() where you will set the text for the Fragment's View using the data you received from Intent; do this in this method because need to make sure the View has been initialized for the Fragment before adding text to it
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // DONE: 2/14/2017  get the data from the new instance of Detail Fragment
        // DONE: 2/14/2017  set the data on the views that will show the data
        TextView titleView = (TextView)getActivity().findViewById(R.id.original_title);
        TextView overvwView = (TextView)getActivity().findViewById(R.id.overview);
        TextView releaseDateView = (TextView)getActivity().findViewById(R.id.release_date);
        TextView voteView = (TextView)getActivity().findViewById(R.id.vote_average);
        //this goes into the fragment, gets the arguments you set for the fragment, and gets the value at index 0
        MovieDetailsParcel movieDetailsParcel = getArguments().getParcelable("movieDetails");
        //get field values from inside parcelable object using getters from the Parcelable class you created

        titleView.setText(movieDetailsParcel.getTitle());
        overvwView.setText(movieDetailsParcel.getOverview());
        releaseDateView.setText(movieDetailsParcel.getRelease());
        voteView.setText(movieDetailsParcel.getVote());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate the layout and use the View hierarchy from the host Activity to tell AndroidOS where to put the Fragment's View in the host Activity's hierarchy
        return inflater.inflate(R.layout.detail_fragment,container, false);
    }

    //don't need to save text for TextViews because already setting fragment's arguments each time fragment gets created and those arguments are set inside the fragment then just use code to set text views

}
