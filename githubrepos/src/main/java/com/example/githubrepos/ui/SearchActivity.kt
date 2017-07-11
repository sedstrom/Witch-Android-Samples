package com.example.githubrepos.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.githubrepos.io.SearchItem
import com.example.githubrepos.io.gitHub
import com.example.githubrepos.utils.StateBinder
import com.example.githubreposearch.R
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity(), SearchActions {

  override fun showOwner(name: String) {
    Toast.makeText(this, "Owned by: $name", Toast.LENGTH_SHORT).show()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val searchItemSelected = BehaviorSubject.create<SearchItem>()

    // Setup recycler view
    val searchItemBinder = SearchAdapterBinder(itemClickListener = View.OnClickListener { v -> searchItemSelected.onNext(v?.tag as SearchItem?) })
    val recyclerView: RecyclerView = findViewById(R.id.recycler_view) as RecyclerView
    recyclerView.adapter = RecyclerViewBinderAdapter.Builder<SearchItem>()
      .binder(searchItemBinder)
      .build()

    recyclerView.layoutManager = LinearLayoutManager(this)

    // Text updates
    val searchEditText = findViewById(R.id.search_et) as EditText
    val searchTextChange: Observable<String> = RxTextView.
      textChanges(searchEditText)
      .map { text -> text.toString() }
      .debounce(600, TimeUnit.MILLISECONDS)

    // Init presenter
    SearchActivityPresenter(
      binder = StateBinder(this),
      actions = this,
      github = gitHub(),
      searchTextChange = searchTextChange,
      searchItemSelected = searchItemSelected)
  }

}
