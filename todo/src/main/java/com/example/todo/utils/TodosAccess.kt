package com.example.todo.utils

import android.app.Activity
import android.view.View
import com.example.todo.App
import com.example.todo.io.TodoStore

fun View.todos(): TodoStore {
  return (this.context as Activity).todos()
}

fun Activity.todos(): TodoStore {
  return(this.application as App).todos
}
