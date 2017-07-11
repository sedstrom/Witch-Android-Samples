package com.example.githubrepos.ui

import com.example.githubrepos.io.Github
import com.example.githubrepos.io.SearchItem
import com.example.githubrepos.io.SearchResponse
import com.example.githubrepos.utils.StateBinder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicInteger

class SearchActivityPresenter(val binder: StateBinder<SearchState>,
                              val actions: SearchActions,
                              github: Github,
                              searchTextChange: Observable<String>,
                              searchItemSelected: Observable<SearchItem>) {

  // Default
  var state: SearchState = SearchState(requests = AtomicInteger(), searchText = "")

  init {
    searchTextChange
      .observeOn(AndroidSchedulers.mainThread())
      .doOnNext { text ->
        state.searchText = text
        binder.bind(state)
      }
      .doOnNext {
        state.requests.incrementAndGet()
        binder.bind(state)
      }
      .observeOn(Schedulers.io())
      .flatMap { searchText ->
        if (searchText.isEmpty()) Observable.just(emptySearchResponse()) else github.searchRepo(searchText)
      }
      .observeOn(AndroidSchedulers.mainThread())
      .doOnNext {
        state.requests.decrementAndGet()
        binder.bind(state)
      }
      .subscribe(
        { searchResponse ->
          state.items = searchResponse.items
          binder.bind(state)
        })

    searchItemSelected.subscribe { searchItem ->
        actions.showOwner(searchItem.owner.login)
    }

    binder.bind(state)
  }

  fun emptySearchResponse(): SearchResponse {
    val searchResponse = SearchResponse()
    searchResponse.items = ArrayList()
    return searchResponse
  }

}