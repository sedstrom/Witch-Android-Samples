package com.example.kittens.utils;

import android.app.Activity;
import android.view.View;

import se.snylt.witch.viewbinder.Witch;

public class StateBinder<State> {

    private final Object viewFinder;

    public StateBinder(Object viewFinder) {
        this.viewFinder = viewFinder;
    }

    public void bind(State target) {
        if(viewFinder instanceof Activity) {
            Witch.bind(target, (Activity) viewFinder);
        } else {
            Witch.bind(target, (View) viewFinder);
        }
    }
}
