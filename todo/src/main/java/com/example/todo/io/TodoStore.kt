package com.example.todo.io

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.*

class TodoStore {

  var todos: List<Todo> = listOf()

  val updated: Subject<List<Todo>> = BehaviorSubject.createDefault(todos)

  fun post(todo: Todo) {
    todos = todos + todo
    updated()
  }

  fun delete(todo: Todo) {
    todos = todos.mapNotNull { t ->  if(todo.id == t.id) null else t  }
    updated()
  }

  fun update(todo: Todo) {
    todos = todos.map { t ->  if(todo.id == t.id) todo else t }
    updated()
  }

  private fun updated() {
    updated.onNext(todos)
  }

  fun createTodo(): Todo {
    return Todo(UUID.randomUUID().toString(), "", false)
  }

}
