package com.example.githubrepos

import android.app.Application
import com.example.githubrepos.io.Github
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

  val github: Github = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build().create(Github::class.java)
}