package com.example.todo.ui.newtodo

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.todo.R
import se.snylt.witch.annotations.BindTo
import se.snylt.witch.annotations.BindToTextView
import se.snylt.witch.annotations.BindWhen
import se.snylt.witch.annotations.Binds
import se.snylt.witch.viewbinder.bindaction.Binder
import se.snylt.witch.viewbinder.bindaction.SyncOnBind


open class NewTodoState {

  var inputText: String = "" @BindWhen(BindWhen.ALWAYS) @BindToTextView(id = R.id.add_todo_et) get

  var keyboardVisible: Boolean = false @BindTo(R.id.add_todo_et) get

  var bindsKeyboardVisible: Binder<EditText, Boolean> = Binder.create(object: SyncOnBind<EditText, Boolean>() {
    override fun onBind(et: EditText?, keyboardVisible: Boolean?) {
      if(keyboardVisible!!) {
        et?.requestFocus()
        val imm = et?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
      } else {
        val imm = et?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(et.windowToken, 0)
      }
    }
  }) @Binds get
}
