package com.example.kittens.ui.grid;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;
import com.example.kittens.ui.pager.PagerActivity;
import com.example.kittens.utils.StateBinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

public class GridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(new RecyclerViewBinderAdapter.Builder<Kitten>()
                .binder(new GridAdapterBinder(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Kitten k = (Kitten) v.getTag();
                        startActivity(PagerActivity.create(GridActivity.this, k));
                    }
                }))
                .build());

        new GridPresenter(new StateBinder<GridState>(this));
    }
}
