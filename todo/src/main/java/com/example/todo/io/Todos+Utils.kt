package com.example.todo.io

val List<Todo>.sizeDone get() = {
  val a = this.sumBy { todo -> if(todo.done) 1 else 0 }
  a
}