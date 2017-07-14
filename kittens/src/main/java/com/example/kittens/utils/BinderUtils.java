package com.example.kittens.utils;

import com.squareup.picasso.Picasso;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

public class BinderUtils {

    public static SyncOnBind<ImageView, String> picasso() {
        return new SyncOnBind<ImageView, String>() {
            @Override
            public void onBind(ImageView imageView, String path) {
                Picasso.with(imageView.getContext()).load(path).into(imageView);
            }
        };
    }

    public static SyncOnBind<View, String> toast() {
        return new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String message) {
                Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
            }
        };
    }
}
