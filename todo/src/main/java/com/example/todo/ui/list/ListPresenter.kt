package com.example.todo.ui.list

import com.example.todo.io.Todo
import com.example.todo.io.TodoStore
import com.example.todo.utils.StateBinder
import io.reactivex.Observable


class ListPresenter(val binder: StateBinder<ListState>,
                    todoStore: TodoStore,
                    addTodoClicks: Observable<Any?>,
                    todoClicked: Observable<Todo>,
                    todoLongClicked: Observable<Todo>) {

  var state = ListState()

  init {

    binder.bind(state)

    addTodoClicks.subscribe {
      state.newTodo = todoStore.createTodo()
      binder.bind(state)
    }

    todoStore.todos.subscribe (
      { todos ->
        state.todos = todos
        state.newTodo = null
        binder.bind(state)
      }
    )

    todoClicked.subscribe { todo ->
      todo.done = !todo.done
      todoStore.update(todo)
    }

    todoLongClicked.subscribe { todo ->
        todoStore.delete(todo)
      }
  }
}