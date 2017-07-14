package com.example.kittens.ui.grid;

import com.example.kittens.R;
import com.example.kittens.io.Kitten;

import android.view.View;
import android.widget.ImageView;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.BindToTextView;
import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.annotations.Binds;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

import static com.example.kittens.utils.BinderUtils.picasso;

class GridAdapterBinder extends RecyclerViewBinderAdapter.Binder<Kitten> {

    private final View.OnClickListener clickListener;

    GridAdapterBinder(View.OnClickListener clickListener) {
        super(R.layout.grid_item);
        this.clickListener = clickListener;
    }

    @Override
    public boolean bindsItem(Object item) {
        return item instanceof Kitten;
    }

    @BindToTextView(id = R.id.name)
    String name() {
        return item.name;
    }

    @BindTo(R.id.image)
    String imageUrl(){
        return item.imageUrl;
    }

    @Binds
    Binder<ImageView, String> bindsImageUrl = Binder.create(picasso());

    @BindToView(id = R.id.grid_item, view = View.class, set = "onClickListener")
    View.OnClickListener onItemClick() {
        return clickListener;
    }

    @BindToView(id = R.id.grid_item, view = View.class, set = "tag")
    Kitten kitten () {
        return item;
    }
}
