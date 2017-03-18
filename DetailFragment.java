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

/*This class creates Detail Fragment that sets text for TextViews in UI*/

public class DetailFragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        //find Views by ID for all TextViews
        TextView titleView = (TextView)getActivity().findViewById(R.id.original_title);
        TextView overvwView = (TextView)getActivity().findViewById(R.id.overview);
        TextView releaseDateView = (TextView)getActivity().findViewById(R.id.release_date);
        TextView voteView = (TextView)getActivity().findViewById(R.id.vote_average);

        //get the arguments from the Fragment and get the Parcelable object with the given key
        MovieDetailsParcel movieDetailsParcel = getArguments().getParcelable("movieDetails");

        //get field values from inside parcelable object and set each TextView
        titleView.setText(movieDetailsParcel.getTitle());
        overvwView.setText(movieDetailsParcel.getOverview());
        releaseDateView.setText("Release Date: " + movieDetailsParcel.getReleaseDate());
        voteView.setText("Average Rating: " + movieDetailsParcel.getVote()+ " /10");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate the Detail Fragment layout
        return inflater.inflate(R.layout.detail_fragment,container, false);
    }


}
