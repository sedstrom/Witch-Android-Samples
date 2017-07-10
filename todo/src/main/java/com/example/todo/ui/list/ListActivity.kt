package com.example.todo.ui.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.todo.App
import com.example.todo.R
import com.example.todo.io.Todo
import com.example.todo.io.TodoStore
import com.example.todo.utils.StateBinder
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.subjects.PublishSubject
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter

class ListActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)
    val toolbar = findViewById(R.id.toolbar) as Toolbar
    setSupportActionBar(toolbar)

    val todoClicked = PublishSubject.create<Todo>()
    val clickListener: View.OnClickListener = View.OnClickListener { view ->
      todoClicked.onNext(view.tag as Todo)
    }

    val todoLongClicked = PublishSubject.create<Todo>()
    val longClickListener: View.OnLongClickListener = View.OnLongClickListener { view ->
      todoLongClicked.onNext(view.tag as Todo)
      true
    }

    val listAdapterBinder = ListAdapterBinder(clickListener, longClickListener)

    val recyclerView: RecyclerView = findViewById(R.id.recycler_view) as RecyclerView
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = RecyclerViewBinderAdapter.Builder<Todo>().binder(listAdapterBinder).build()

    ListPresenter(
      StateBinder(this),
      todos(),
      RxView.clicks(findViewById(R.id.add_todo_fab)),
      todoClicked,
      todoLongClicked)
  }

  private fun todos(): TodoStore {
    return (application as App).todos
  }
}

