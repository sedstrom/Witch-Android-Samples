package com.example.githubrepos.io

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class SearchResponse(val items: Array<SearchItem>)
data class SearchItem(val id: Int, val name: String, val owner: Owner)
@Parcelize data class Owner(val avatar_url: String, val login: String) : Parcelable