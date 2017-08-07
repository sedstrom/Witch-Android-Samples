package com.example.todo.ui.list

import android.view.View
import com.example.githubreposearch.*
import com.example.todo.R
import com.example.todo.io.Todo
import com.example.todo.io.sizeDone
import com.example.todo.ui.newtodo.NewTodoView
import se.snylt.witch.annotations.*
import se.snylt.witch.viewbinder.bindaction.Binder
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter

class ListState {

  var todos: List<Todo> = arrayListOf()
    @BindWhen(BindWhen.NOT_SAME)
    @BindToRecyclerView(id = R.id.recycler_view, adapter = RecyclerViewBinderAdapter::class, set = "items")
    get

  val hint: String
    @BindToTextView(id = R.id.hint) get() {
      return if (todos.isEmpty()) "Add your first todo! :-)" else ""
    }

  var newTodo: Todo? = null @BindTo(R.id.new_todo) get

  val bindsNewTodo: Binder<NewTodoView, Todo> = Binder.create<NewTodoView, Todo>()
    .invisibleOnce().slideOutBottomIfNull().slideInBottomIfNotNull().setTodo()
    @Binds get

  val fabVisible: Int @BindTo(R.id.add_todo_fab) get() = if(newTodo == null) View.VISIBLE else View.INVISIBLE

  val bindsFabVisible: Binder<View, Int> = Binder.create<View, Int>()
    .scaleUpIfVisible().scaleDownIfNotVisible().setVisiblity() @Binds get

  val progress: String @BindTo(R.id.progress_tv) get() = "${todos.sizeDone()}/${todos.size}"

  val bindsProgress = Binder.create<android.widget.TextView, String>()
    .flipOut().setText().flipIn() @Binds get
}