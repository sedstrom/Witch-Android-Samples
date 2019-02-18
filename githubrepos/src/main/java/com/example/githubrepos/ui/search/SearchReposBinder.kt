package com.example.githubrepos.ui.search

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.githubrepos.io.Owner
import com.example.githubrepos.io.SearchItem
import com.example.githubrepos.util.TextWatcherAdapter
import com.example.githubreposearch.R
import com.squareup.picasso.Picasso
import se.snylt.witch.android.Witch
import se.snylt.witch.android.recyclerview.WitchRecyclerViewAdapter
import se.snylt.witch.annotations.*
import se.snylt.witch.annotations.BindWhen.NOT_SAME

open class SearchReposBinder(val model: SearchReposViewModel,
                             val view: SearchReposActivity,
                             val onShowOwner : (Owner)->(Unit)) {

  init {
    model.addObserver { Witch.bind(this, view) }
    Witch.bind(this, view)
  }

  @Setup(id = R.id.recycler_view)
  fun setupRecyclerView(rv: RecyclerView) {
    rv.layoutManager = LinearLayoutManager(rv.context)
    rv.adapter = WitchRecyclerViewAdapter.Builder<SearchItem>()
      .binder(SearchItemBinder())
      .build()
  }

  @Setup(id = R.id.search_et)
  fun setupSearch(et: EditText) {
    et.addTextChangedListener(object: TextWatcherAdapter() {
      override fun afterTextChanged(p0: Editable?) {
        model.onSearchTextChanged(p0.toString())
      }
    })
  }

  @Data fun searchItems(): Array<SearchItem>? {
    return model.response.items
  }

  @Bind(id = R.id.recycler_view) @BindWhen(NOT_SAME)
  fun bindSearchItems(rv: RecyclerView, searchItems: Array<SearchItem>) {
    (rv.adapter as WitchRecyclerViewAdapter<*>).items = searchItems.toList()
  }

  @BindData(id = R.id.progress, view = View::class, set = "visibility")
  fun progressVisibility(): Int {
    return if (isSearching()) { View.VISIBLE } else { View.GONE }
  }

  fun isSearching(): Boolean {
    return model.searchedText != model.searchText
  }

  fun hasResults(): Boolean {
    return !model.response.items.isNullOrEmpty()
  }

  @BindData(id = R.id.results_info_tv, view = TextView::class, set = "text")
  fun searchInfo(): String {
    if (model.searchText.isEmpty()) {
      return "Type something to search for a github repo!"
    } else if (!isSearching() && !hasResults()) {
      return "No results for '${model.searchedText}'"
    } else {
      return ""
    }
  }

  inner class SearchItemBinder : WitchRecyclerViewAdapter.Binder<SearchItem>(R.layout.search_item, SearchItem::class.java) {

    @BindData(id = R.id.search_item, view = View::class, set = "tag")
    fun owner(): Owner { return item.owner }

    @BindData(id = R.id.name, view = TextView::class, set = "text")
    fun name(): String { return item.name }

    @Bind(id = R.id.avatar)
    fun image(iv: ImageView) {
      Picasso.with(iv.context).load(item.owner.avatar_url).into(iv)
    }

    val onClick = View.OnClickListener() {
      this@SearchReposBinder.onShowOwner(it.getTag() as Owner)
    } @BindData(id = R.id.search_item, view = View::class, set="onClickListener") get
  }
}


