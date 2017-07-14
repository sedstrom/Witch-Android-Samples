package com.example.kittens.ui.pager;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.Binds;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

import static com.example.kittens.utils.BinderUtils.toast;

class PagerState {

    List<Kitten> kittens = new ArrayList<>();

    int startPosition = 0;

    @BindTo(R.id.view_pager)
    final PagerState state = this;

    // This is a bit of a hack. Binding has to happen in a certain order, which is not yet supported.
    @Binds
    Binder<ViewPager, PagerState> bindsState = Binder.create(new SyncOnBind<ViewPager, PagerState>() {
        @Override
        public void onBind(ViewPager viewPager, PagerState pagerState) {
            ((KittenPagerAdapter)viewPager.getAdapter()).setKittens(pagerState.kittens);
            viewPager.setCurrentItem(startPosition, false);
        }
    });

    @BindTo(R.id.view_pager)
    String instructions = "Swipe to see more kittens :)";

    @Binds
    Binder<View, String> bindsInstructions = Binder.create(toast());


}
