package com.example.todo.utils

import android.app.Activity
import android.view.View
import com.example.todo.App
import com.example.todo.io.TodoStore

fun Activity.todos(): TodoStore {
  return(this.application as App).todos
}
