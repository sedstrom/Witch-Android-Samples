package com.example.kittens.ui.pager;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.Binds;
import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.bindaction.Binder;

import static com.example.kittens.utils.BinderUtils.picasso;

class KittenPagerAdapter extends PagerAdapter {

    private PagerBinder binder = new PagerBinder();

    private List<Kitten> kittens;

    @Override
    public int getCount() {
        return kittens != null ? kittens.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item, container, false);
        container.addView(view);
        binder.bind(view, kittens.get(position));
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setKittens(List<Kitten> kittens) {
        this.kittens = kittens;
        notifyDataSetChanged();
    }

    static class PagerBinder {

        private Kitten kitten;

        @BindTo(R.id.image)
        String imageUrl() {
            return kitten.imageUrl;
        }

        @Binds
        Binder bindsImageUrl = Binder.create(picasso());

        private void bind(View view, Kitten kitten) {
            this.kitten = kitten;
            Witch.bind(this, view);
        }

    }
}
