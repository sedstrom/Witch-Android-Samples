package com.example.githubreposearch

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AnticipateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.example.todo.io.Todo
import com.example.todo.ui.newtodo.NewTodoView
import se.snylt.witch.viewbinder.bindaction.AsyncOnBind
import se.snylt.witch.viewbinder.bindaction.Binder
import se.snylt.witch.viewbinder.bindaction.OnBindListener
import se.snylt.witch.viewbinder.bindaction.SyncOnBind

fun Binder<NewTodoView, Todo>.setTodo(): Binder<NewTodoView, Todo> {

  return next(object: SyncOnBind<NewTodoView, Todo>() {
    override fun onBind(target: NewTodoView?, value: Todo?) {
        target?.todo = value
    }
  })
}

fun <V: View, Value: Any?> Binder<V, Value>.invisibleOnce(): Binder<V, Value> {
  var invisible = false
  return next(object: SyncOnBind<V, Value>() {
    override fun onBind(target: V?, value: Value?) {
      if(!invisible) {
        target?.visibility = INVISIBLE
      }
      invisible = true
    }
  })
}

fun <V: View, Value: Any?> Binder<V, Value>.slideInBottomIfNotNull(): Binder<V, Value> {

  return next(object: SyncOnBind<V, Value>() {
    override fun onBind(target: V?, value: Value?) {
      if(value != null) {
        target?.visibility = VISIBLE
        val slideIn = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, target!!.measuredHeight.toFloat(), 0f)
        slideIn.duration = 500
        slideIn.interpolator = DecelerateInterpolator(2.5f)
        slideIn.start()
      }
    }
  })
}

fun <V: View, Value: Any?> Binder<V, Value>.slideOutBottomIfNull(): Binder<V, Value> {

  return next(object: AsyncOnBind<V, Value>() {
    override fun onBind(target: V?, value: Value?, onBindDone: OnBindListener) {
      if(value == null) {
        val slideOut = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, target!!.measuredHeight.toFloat())
        slideOut.duration = 300
        slideOut.addListener(object: AnimatorListenerAdapter() {
          @Override override fun onAnimationEnd(animation: Animator?) {
            target?.visibility = INVISIBLE
            onBindDone.onBindDone()
          }
        })
        slideOut.start()
      } else {
        onBindDone.onBindDone()
      }
    }
  })
}

fun <V: View> Binder<V, Int>.scaleDownIfNotVisible(): Binder<V, Int> {

  return next(object: AsyncOnBind<V, Int>() {
    override fun onBind(target: V?, value: Int?, onBindDone: OnBindListener) {
      if(value!! != VISIBLE) {
        val scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0f)
        val scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0f)
        val a = AnimatorSet()
        a.playTogether(scaleX, scaleY)
        a.duration = 250
        a.addListener(object: AnimatorListenerAdapter() {
          @Override override fun onAnimationEnd(animation: Animator?) {
            onBindDone.onBindDone()
          }
        })
        a.start()
      } else {
        onBindDone.onBindDone()
      }
    }
  })
}

fun <V: View> Binder<V, Int>.scaleUpIfVisible(): Binder<V, Int> {

  return next(object: SyncOnBind<V, Int>() {
    override fun onBind(target: V?, value: Int?) {
      if(value!! == VISIBLE) {
        val scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 1f)
        val scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 1f)
        val a = AnimatorSet()
        a.playTogether(scaleX, scaleY)
        a.duration = 250
        a.start()
      }
    }
  })
}

fun <V: View> Binder<V, Int>.setVisiblity(): Binder<V, Int> {
  return next(object: SyncOnBind<V, Int>() {
    override fun onBind(target: V?, value: Int?) {
      target?.visibility = value!!
    }
  })
}

fun <V: View, Value> Binder<V, Value>.flipOut(): Binder<V, Value> {
  return next(object: AsyncOnBind<V, Value>() {
    override fun onBind(target: V?, value: Value?, onBindDone: OnBindListener) {
      val flip = ObjectAnimator.ofFloat(target, View.ROTATION_Y, 0f, -90f)
      flip.interpolator = AnticipateInterpolator()
      flip.duration = 400
      flip.addListener(object: AnimatorListenerAdapter() {
        @Override override fun onAnimationEnd(animation: Animator?) {
          onBindDone.onBindDone()
        }
      })
      flip.start()
    }
  })
}

fun <V: View, Value> Binder<V, Value>.flipIn(): Binder<V, Value> {
  return next(object: SyncOnBind<V, Value>() {
    override fun onBind(target: V?, value: Value?) {
      val flip = ObjectAnimator.ofFloat(target, View.ROTATION_Y, 90f, 0f)
      flip.interpolator = OvershootInterpolator()
      flip.duration = 400
      flip.start()
    }
  })
}

fun <V: TextView> Binder<V, String>.setText(): Binder<V, String> {
  return next(object: SyncOnBind<V, String>() {
    override fun onBind(target: V?, value: String?) {
      target?.text = value
    }
  })
}