package com.example.todo.ui.list

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import com.example.todo.R
import com.example.todo.io.Todo
import se.snylt.witch.annotations.*
import se.snylt.witch.viewbinder.bindaction.Binder
import se.snylt.witch.viewbinder.bindaction.SyncOnBind
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter



class ListAdapterBinder(val todoClickedListener: View.OnClickListener, val todoLongClickedListener: View.OnLongClickListener) : RecyclerViewBinderAdapter.Binder<Todo>(R.layout.todo_list_item) {

  override fun bindsItem(item: Any?): Boolean {
    return item is Todo
  }

  val name: String @BindToTextView(id = R.id.name) get() = item.name

  val done: Boolean @BindWhen(BindWhen.NOT_SAME) @BindTo(R.id.name) get() = item.done

  val bindsDone =  Binder.create(object: SyncOnBind<TextView, Boolean>(){
    override fun onBind(textView: TextView?, done: Boolean?) {
      if(done!!) {
        textView?.paintFlags = (textView!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
      } else {
        textView?.paintFlags = (textView!!.paintFlags and 0xffef) // Flipped
      }
    }
  })
  @Binds
  get

  val click: View.OnClickListener
    @BindToView(id = R.id.list_item, view = View::class, set = "onClickListener") get() = todoClickedListener

  val longClick: View.OnLongClickListener
    @BindToView(id = R.id.list_item, view = View::class, set = "onLongClickListener") get() = todoLongClickedListener


  val tag: Todo @BindToView(id = R.id.list_item, view = View::class, set = "tag") get() = item

}