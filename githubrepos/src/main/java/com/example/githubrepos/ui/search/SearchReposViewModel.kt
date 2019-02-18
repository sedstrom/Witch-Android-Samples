package com.example.githubrepos.ui.search

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.githubrepos.io.ActiveSearch
import com.example.githubrepos.io.SearchResponse
import com.example.githubrepos.util.ObservableViewModel
import io.reactivex.disposables.Disposable

class SearchReposViewModel(val search: ActiveSearch): ObservableViewModel() {

  // Model
  var response: SearchResponse by trigger(SearchResponse(arrayOf()))
  var searchText: String by trigger("")
  var searchedText: String by trigger("")

  private var searchDisposable: Disposable? = null

  init {
    searchDisposable = search.observe()
      .subscribe(
        { response: SearchResponse ->
          this.searchedText = this.searchText
          this.response = response
        },
        { error: Throwable ->
          this.searchedText = this.searchText
          this.response = emptyResponse()
        })
  }

  private fun emptyResponse(): SearchResponse {
    return SearchResponse(arrayOf())
  }

  fun onSearchTextChanged(text: String) {
    this.searchText = text
    this.response = emptyResponse()
    search.update(text)
  }

  override fun onCleared() {
    super.onCleared()
    searchDisposable?.dispose()
  }
}

open class SearchReposViewModelFactory(private val search: ActiveSearch): ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return SearchReposViewModel(search) as T
  }

}