package com.example.todo.utils

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun EditText.imeDone(): Observable<Unit> {
  val imeDone = PublishSubject.create<Unit>()
  this.setOnEditorActionListener { v, actionId, event ->
    if ((event?.keyCode == android.view.KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
      imeDone.onNext(Unit)
      true
    }
    false
  }
  return imeDone
}
