package com.example.todo.utils

import android.app.Activity
import android.util.Log
import android.view.View
import se.snylt.witch.viewbinder.Witch

class StateBinder<in State> (val viewFinder: Any) {

  fun bind(state: State) {
    if (viewFinder is Activity) {
      Witch.bind(state, viewFinder)
    } else if (viewFinder is View) {
      Witch.bind(state, viewFinder)
    }
    Log.d("State", state.toString())
  }
}


