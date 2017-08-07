package com.example.kittens.ui.pager;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;
import com.example.kittens.utils.StateBinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class PagerActivity extends AppCompatActivity {

    Subject<Boolean> activityStopped = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setAdapter(new KittenPagerAdapter());

        new PagerPresenter(new StateBinder<PagerState>(this), getIntent().getIntExtra("id", 0));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static Intent create(Context context, Kitten kitten) {
        Intent i = new Intent(context, PagerActivity.class);
        i.putExtra("id", kitten.id);
        return i;
    }
}
