package com.example.kittens.ui.grid;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.annotations.BindToRecyclerView;
import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

class GridState {

    @BindToRecyclerView(id = R.id.recycler_view, adapter = RecyclerViewBinderAdapter.class, set = "items")
    List<Kitten> items = new ArrayList<>();

    @BindToView(id = R.id.progress_bar, view = View.class, set = "visibility")
    int progressVisibility() {
        return items.isEmpty() ? VISIBLE : GONE;
    }

}
