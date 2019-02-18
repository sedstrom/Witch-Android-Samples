package com.example.githubrepos.io

import android.app.Activity
import com.example.githubrepos.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.observable.ObservableJust
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface Github {
  @GET("search/repositories")
  fun searchRepo(@Query("q") query: String): Observable<SearchResponse>
}

fun Activity.github(): Github {
  return (application as App).github
}

fun Github.activeSearch(): ActiveSearch  {
  return ActiveSearch(this)
}

class ActiveSearch(val github: Github) {

  private var searchTextChanged = PublishSubject.create<String>()

  fun update(text: String) {
    searchTextChanged.onNext(text)
  }

  fun observe(): Observable<SearchResponse> {
    return searchTextChanged
      .debounce(500, TimeUnit.MILLISECONDS)
      .concatMap {
        if(it.isEmpty()) {
          ObservableJust(SearchResponse(arrayOf()))
        } else {
          github.searchRepo(it)
            .takeUntil(searchTextChanged)
        }
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
  }
}