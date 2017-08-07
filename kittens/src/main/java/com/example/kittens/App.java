package com.example.kittens;


import android.app.Application;

import se.snylt.witch.viewbinder.Witch;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Witch.setLoggingEnabled(true);
    }
}
