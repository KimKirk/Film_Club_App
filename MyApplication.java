package com.spellflight.android.popularmovies;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kim Kirk on 11/10/2016.
 * MyApplication gets application context
 */


public class MyApplication extends Application {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return mContext;
    }
}
