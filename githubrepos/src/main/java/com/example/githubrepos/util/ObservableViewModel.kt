package com.example.githubrepos.util

import android.arch.lifecycle.ViewModel
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

open class ObservableViewModel: ViewModel() {

  private var observers: List<()->(Unit)> = listOf()

  fun addObserver(observer: ()->(Unit)) {
    observers += observer
  }

  fun removeObserver(observer: ()->(Unit)) {
    observers -= observer
  }

  private var onChanged = {
    observers.forEach { it() }
  }

  /**
   * Register property that will trigger observer notification when changed.
   */
  fun <T> trigger(initialValue: T): Trigger<T> {
    return Trigger(initialValue, onChanged)
  }

  class Trigger<T>(initialValue: T, val onChange: ()->(Unit)): ObservableProperty<T>(initialValue) {

    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
      super.afterChange(property, oldValue, newValue)
      onChange()
    }
  }
}