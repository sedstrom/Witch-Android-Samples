package com.example.todo.ui

import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.*
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.example.todo.R
import com.example.todo.io.Todo
import com.example.todo.utils.todos
import se.snylt.witch.android.Witch
import se.snylt.witch.android.recyclerview.WitchRecyclerViewAdapter
import se.snylt.witch.annotations.Bind
import se.snylt.witch.annotations.BindData
import se.snylt.witch.annotations.BindWhen
import se.snylt.witch.annotations.Data

class TodoActivity : AppCompatActivity() {

  var todoDraft: Todo? = null
    set(todo) {
    field = todo
    bind()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)
    todos().updated.subscribe {
      bind()
    }
  }

  fun bind() {
    Witch.bind(this, this)
  }

  @Bind(id = R.id.recycler_view) @BindWhen(BindWhen.ONCE)
  fun setupRecyclerView(view: RecyclerView) {
    view.layoutManager = LinearLayoutManager(this)
    view.adapter = WitchRecyclerViewAdapter.Builder<Todo>()
            .binder(TodoBinder())
            .build()
  }

  @Bind(id = R.id.toolbar) @BindWhen(BindWhen.ONCE)
  fun setupToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
  }

  @Bind(id = R.id.add_todo_fab) @BindWhen(BindWhen.ONCE)
  fun setupAddTodo(view: View) {
    view.setOnClickListener {
      todoDraft = todos().createTodo()
    }
  }

  val invisibleFirstBind: Int = View.INVISIBLE
    @BindData(id = R.id.new_todo, view = View::class, set = "visibility")
    @BindWhen(BindWhen.ONCE) get

  @Data
  fun showNewTodo(): Boolean {
    return todoDraft != null
  }

  @Bind(id = R.id.new_todo)
  fun showNewTodo(view: View, show: Boolean) {
    if (show) {
      view.translationY = view.height.toFloat()
      view.animate()
              .translationY(0f)
              .setInterpolator(DecelerateInterpolator(2f))
              .setDuration(500)
              .start()
      view.visibility = View.VISIBLE
    } else {
      view.animate()
              .translationY(view.height.toFloat())
              .setDuration(500)
              .setInterpolator(DecelerateInterpolator(2f))
              .withEndAction({
                view.visibility = View.INVISIBLE
              })
              .start()
    }
  }

  val hint: String
  @BindData(id = R.id.hint, view = TextView::class, set = "text")
  get() {
    if (todos().todos.isEmpty()) {
      return "Add your first todo :-)"
    } else {
      return ""
    }
  }

  @Data
  fun addTodoVisible(): Boolean {
    return todoDraft == null
  }

  @Bind(id = R.id.add_todo_fab)
  fun addTodoVisible(view: View, show: Boolean) {
    if (show) {
      view.animate().alpha(1f).setDuration(250).start()
    } else {
      view.animate().alpha(0f).setDuration(250).start()
    }
  }

  @Data @BindWhen(BindWhen.NOT_SAME)
  fun items(): List<Todo> {
    return todos().todos
  }

  @Bind(id = R.id.recycler_view)
  fun items(recyclerView: RecyclerView, items: List<Todo>) {
    (recyclerView.adapter as WitchRecyclerViewAdapter<*>).items = items
  }

  @Bind(id = R.id.add_todo_et) @BindWhen(BindWhen.ONCE)
  fun setupInput(view: EditText) {

    // Text changed
    view.addTextChangedListener(object: TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
      override fun afterTextChanged(s: Editable?) {
        todoDraft?.name = s.toString()
      }
    })

    // IME
    view.setOnEditorActionListener { v, actionId, event ->
      if ((event?.keyCode == android.view.KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
        val addTodo = todoDraft
        todoDraft = null
        todos().post(addTodo!!)
        view.text = null
      }
      false
    }
  }

  inner class TodoBinder(layoutId: Int = R.layout.todo_list_item) :
          WitchRecyclerViewAdapter.Binder<Todo>(layoutId) {

    @Bind(id = R.id.name)
    fun name(view: TextView) {
      view.setText(item.name)
    }

    @BindData(id = R.id.list_item, view = View::class, set = "tag")
    fun tagCurrentItem(): Todo {
      return item
    }

    val clicked = OnClickListener {
      val todo = it.getTag() as Todo
      todo.done = !todo.done
      todos().update(todo)
    } @BindData(id = R.id.list_item, view = View::class, set = "onClickListener")
      get

    val longClicked = OnLongClickListener() {
      val todo = it.getTag() as Todo
      todos().delete(todo)
      true
    } @BindData(id = R.id.list_item, view = View::class, set = "onLongClickListener")
      get

    @Bind(id = R.id.name)
    fun done(name: TextView) {
      if (item.done) {
        name.paintFlags = (name.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
      } else {
        name.paintFlags = (name.paintFlags and 0xffef)
      }
    }

    override fun bindsItem(item: Any?): Boolean {
      return item is Todo
    }
  }
}

