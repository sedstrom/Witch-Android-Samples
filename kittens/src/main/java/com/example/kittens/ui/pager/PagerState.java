package com.example.kittens.ui.pager;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.annotations.BindToViewPager;
import se.snylt.witch.annotations.Binds;
import se.snylt.witch.viewbinder.bindaction.Binder;

import static com.example.kittens.utils.BinderUtils.toast;

class PagerState {

    // Views are bound in same order as declared
    // kittens needs to be bound before startPosition
    @BindToViewPager(id = R.id.view_pager, adapter = KittenPagerAdapter.class, set = "kittens")
    List<Kitten> kittens = new ArrayList<>();

    @BindToView(id = R.id.view_pager, view = ViewPager.class, set = "currentItem")
    int startPosition = 0;

    @BindTo(R.id.view_pager)
    String instructions = "Swipe to see more kittens :)";

    @Binds
    Binder<View, String> bindsInstructions = Binder.create(toast());

}
