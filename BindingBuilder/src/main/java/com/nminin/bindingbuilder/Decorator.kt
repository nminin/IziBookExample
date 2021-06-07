package com.nminin.bindingbuilder

import android.view.View

abstract class Decorator<V: View, T>() {
    abstract fun bind(view: V, item: T)
}