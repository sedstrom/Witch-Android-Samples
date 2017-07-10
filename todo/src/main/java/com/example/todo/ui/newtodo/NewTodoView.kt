package com.example.todo.ui.newtodo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.example.todo.R
import com.example.todo.io.Todo
import com.example.todo.utils.StateBinder
import com.example.todo.utils.imeDone
import com.example.todo.utils.todos
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.subjects.BehaviorSubject

class NewTodoView : android.widget.LinearLayout {

  val visibilityChanges: BehaviorSubject<Int> = BehaviorSubject.create<Int>()

  var todo: Todo? get() {
    return oTodo.value
  }
  set(todo) {
    if(todo != null) {
      oTodo.onNext(todo)
    }
  }

  val oTodo: BehaviorSubject<Todo> = BehaviorSubject.create()

  constructor(context: Context) : this(context = context, attrs = null, style = 0)

  constructor(context: Context, attrs: AttributeSet) : this(context = context, attrs = attrs, style = 0)

  constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style) {
    inflate(context, R.layout.add_todo, this)

    NewTodoPresenter(
      StateBinder(this),
      oTodo,
      todos(),
      RxTextView.textChanges(findViewById(R.id.add_todo_et) as EditText),
      (findViewById(R.id.add_todo_et) as EditText).imeDone(),
      visibilityChanges.distinctUntilChanged())
  }

  override fun onVisibilityChanged(changedView: View?, visibility: Int) {
    visibilityChanges.onNext(visibility)
  }
}
