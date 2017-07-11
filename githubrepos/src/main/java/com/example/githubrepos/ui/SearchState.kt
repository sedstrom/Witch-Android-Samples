package com.example.githubrepos.ui

import android.view.View
import com.example.githubreposearch.R
import com.example.githubrepos.io.SearchItem
import se.snylt.witch.annotations.BindToRecyclerView
import se.snylt.witch.annotations.BindToTextView
import se.snylt.witch.annotations.BindToView
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter
import java.util.concurrent.atomic.AtomicInteger

open class SearchState(val requests: AtomicInteger = AtomicInteger(), var searchText: String) {

  var items: ArrayList<SearchItem> = arrayListOf()
    @BindToRecyclerView(id =  R.id.recycler_view, adapter = RecyclerViewBinderAdapter::class, set = "items")
    get

  val progressVisibility: Int
    @BindToView(id = R.id.progress, view = View::class, set = "visibility")
    get() = if (requests.get() > 0) View.VISIBLE else View.INVISIBLE

  val infoText: String
    @BindToTextView(id = R.id.results_info_tv)
    get() =
      if (searchText.isEmpty()) {
        "Type something to search for a repo"
      } else if(items.isEmpty()) {
        if (requests.get() > 0) "Searching..." else "No results :´("
      } else {
        ""
      }

  override fun toString(): String {
    return "[ \n requests: ${requests.get()}\n items: ${items.size}\n searchText: $searchText\n]"
  }

}