package com.example.githubrepos.io

open class SearchResponse {

  lateinit var items: ArrayList<SearchItem>
}

open class SearchItem {

  var id: Int = 0

  lateinit var name: String

  lateinit var owner: Owner
}

open class Owner {

  lateinit var avatar_url: String

  lateinit var login: String


}