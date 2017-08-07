package com.example.todo

import android.app.Application
import com.example.todo.io.TodoStore
import se.snylt.witch.viewbinder.Witch

class App: Application() {

  val todos = TodoStore()

  override fun onCreate() {
    super.onCreate()
    Witch.setLoggingEnabled(true)
  }

}
