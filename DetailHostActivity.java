package com.spellflight.android.popularmovies;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * Created by Kim Kirk on 2/3/2017.
 */

/*
This class hosts the Detail Fragment
 */

public class DetailHostActivity extends FragmentActivity {
    //holds new instance of DetailFragment
    private DetailFragment mDetailFragment = new DetailFragment();

    //sets layout container in host Activity for Fragment and attaches Fragment to host Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //adds space in host Activity View Hierarchy for Fragment container
        setContentView(R.layout.detail_fragment_container);

        //re-creates fragment instance when Bundle is full
        if(savedInstanceState != null) {
            //gets Fragment from Bundle
            DetailFragment fragment = (DetailFragment)getSupportFragmentManager().getFragment(savedInstanceState,"fragment");

            //replaces any current fragment with fragment instance from Bundle
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment).commit();
        }
        //if Bundle is empty then create new Fragment with arguments
        else {
            //set arguments for Fragment
            mDetailFragment.setArguments(getIntent().getExtras());

            //add Fragment to host Activity
            getSupportFragmentManager().beginTransaction().add(R.id.detail_container, mDetailFragment).commit();
        }
    }


    //saves fragment instance before fragment is destroyed
    @Override
    protected void onSaveInstanceState(Bundle outState){
        //get the current Fragment instance from the SupportFragmentManager and put into Bundle
        getSupportFragmentManager().putFragment(outState,"fragment",getSupportFragmentManager().findFragmentById(R.id.detail_container));

        //save the Bundle
        super.onSaveInstanceState(outState);
    }
}
