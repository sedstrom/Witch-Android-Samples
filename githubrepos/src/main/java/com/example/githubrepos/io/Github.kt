package com.example.githubrepos.io

import android.app.Activity
import com.example.githubrepos.App
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Github {

  @GET("search/repositories")
  fun searchRepo(@Query("q") query: String): Observable<SearchResponse>
}

fun Activity.gitHub(): Github {
  return (application as App).github
}