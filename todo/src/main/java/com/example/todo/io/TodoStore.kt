package com.example.todo.io

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*

class TodoStore {

  val subject: BehaviorSubject<List<Todo>> = BehaviorSubject.createDefault(listOf())

  val todos : Observable<List<Todo>> get() = subject

  fun post(todo: Todo) {
    subject.onNext(subject.value + todo)
  }

  fun delete(todo: Todo) {
    val todos = subject.value.mapNotNull { t ->  if(todo.id == t.id) null else t  }
    subject.onNext(todos)
  }

  fun update(todo: Todo) {
    val todos = subject.value.map { t ->  if(todo.id == t.id) todo else t }
    subject.onNext(todos)
  }

  fun createTodo(): Todo {
    return Todo(UUID.randomUUID().toString(), "", false)
  }

}
