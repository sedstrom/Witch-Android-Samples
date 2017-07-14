package com.example.kittens.ui.pager;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;
import com.example.kittens.utils.StateBinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class PagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(new KittenPagerAdapter());

        new PagerPresenter(new StateBinder<PagerState>(this), getIntent().getIntExtra("id", 0));
    }

    public static Intent create(Context context, Kitten kitten) {
        Intent i = new Intent(context, PagerActivity.class);
        i.putExtra("id", kitten.id);
        return i;
    }
}
