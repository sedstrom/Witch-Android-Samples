package com.example.githubrepos.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.githubrepos.io.SearchItem
import com.example.githubrepos.utils.fadeIn
import com.example.githubrepos.utils.picasso
import com.example.githubrepos.utils.setText
import com.example.githubreposearch.R
import se.snylt.witch.annotations.BindTo
import se.snylt.witch.annotations.BindToView
import se.snylt.witch.annotations.Binds
import se.snylt.witch.viewbinder.bindaction.Binder
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter

class SearchAdapterBinder(val itemClickListener: View.OnClickListener) : RecyclerViewBinderAdapter.Binder<SearchItem>(R.layout.search_item) {

  override fun bindsItem(item: Any?): Boolean {
    return item is SearchItem
  }

  val name: String
    @BindTo(R.id.name)
    get() = item.name

  val bindsName = Binder.create<TextView, String>().setText().fadeIn() @Binds get

  val searchItem: SearchItem @BindToView(id = R.id.search_item, view = View::class, set = "tag") get() = item

  val avatar: String @BindTo(R.id.avatar)
    get() = item.owner.avatar_url

  val bindsAvatar = Binder.create<ImageView, String>().picasso() @Binds get

  val clickListener: View.OnClickListener @BindToView(id = R.id.search_item, view = View::class, set = "onClickListener") get() = itemClickListener
}