package com.example.kittens.ui.pager;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.BindToViewPager;
import se.snylt.witch.annotations.Binds;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

import static com.example.kittens.utils.BinderUtils.toast;

class PagerState {

    // Views are bound in same order as declared
    // kittens needs to be bound before startPosition
    @BindToViewPager(id = R.id.view_pager, adapter = KittenPagerAdapter.class, set = "kittens")
    List<Kitten> kittens = new ArrayList<>();

    @BindTo(R.id.view_pager)
    int startPosition = 0;

    @Binds
    Binder<ViewPager, Integer> bindsStartPosition = Binder.create(new SyncOnBind<ViewPager, Integer>() {
        @Override
        public void onBind(ViewPager viewPager, Integer startPosition) {

            viewPager.setCurrentItem(startPosition, false);
        }
    });

    @BindTo(R.id.view_pager)
    String instructions = "Swipe to see more kittens :)";

    @Binds
    Binder<View, String> bindsInstructions = Binder.create(toast());

}
