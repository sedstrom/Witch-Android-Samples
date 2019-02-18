package com.example.githubrepos.ui.search

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.githubrepos.io.Owner
import com.example.githubrepos.io.activeSearch
import com.example.githubrepos.io.github
import com.example.githubreposearch.R

class SearchReposActivity: AppCompatActivity() {

  private lateinit var binder: SearchReposBinder

  private val showOwner = { owner: Owner ->
    Toast.makeText(this, "Repo owner: ${owner.login}", Toast.LENGTH_SHORT).show()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val viewModel = ViewModelProviders.of(this,
      SearchReposViewModelFactory((github().activeSearch())))
      .get(SearchReposViewModel::class.java)
    binder = SearchReposBinder(viewModel, this, showOwner)
  }
}