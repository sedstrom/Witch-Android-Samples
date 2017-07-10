package com.example.todo

import android.app.Application
import com.example.todo.io.TodoStore

class App: Application() {

  val todos = TodoStore()

}
