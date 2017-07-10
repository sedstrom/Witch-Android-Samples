package com.example.todo.ui.newtodo

import android.view.View.VISIBLE
import com.example.todo.io.Todo
import com.example.todo.io.TodoStore
import com.example.todo.utils.StateBinder
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class NewTodoPresenter(val binder: StateBinder<NewTodoState>, val todo: Observable<Todo>,
                       val todoStore: TodoStore, textChanged: Observable<CharSequence>, doneClicked: Observable<Unit>,
                       visibilityChanges: Observable<Int>) {

  var state = NewTodoState()

  init {
    binder.bind(state)

    visibilityChanges.subscribe { visibility ->
      state.keyboardVisible = (visibility == VISIBLE)
      binder.bind(state)
    }

    textChanged.sample(doneClicked).withLatestFrom(todo, BiFunction<CharSequence, Todo, Todo> { text, t ->
      t.name = text.toString()
      t
    }).subscribe({ todo ->
      state.inputText = ""
      binder.bind(state)
      todoStore.post(todo)
    })
  }

}
