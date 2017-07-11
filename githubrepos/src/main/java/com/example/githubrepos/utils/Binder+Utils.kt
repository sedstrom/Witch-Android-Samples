package com.example.githubrepos.utils

import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import se.snylt.witch.viewbinder.bindaction.Binder
import se.snylt.witch.viewbinder.bindaction.SyncOnBind

fun Binder<ImageView, String>.picasso(): Binder<ImageView, String> {

  return next(object: SyncOnBind<ImageView, String>() {
    override fun onBind(target: ImageView?, value: String?) {
      Picasso.with(target!!.context).load(value!!).into(target)
    }
  })
}

fun <T: View, V: Any> Binder<T, V>.fadeIn(): Binder<T, V> {

  return next(object: SyncOnBind<T, V>() {
    override fun onBind(target: T?, value: V?) {
      val a = ObjectAnimator.ofFloat(target!!, View.ALPHA, 0f , 1f)
        .setDuration(400)
        a.start()
      }
  })
}

fun Binder<TextView, String>.setText(): Binder<TextView, String> {

  return next(object: SyncOnBind<TextView, String>() {
    override fun onBind(target: TextView?, value: String?) {
        target?.text = value
    }
  })
}