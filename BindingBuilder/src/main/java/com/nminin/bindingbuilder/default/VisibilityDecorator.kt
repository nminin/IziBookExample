package com.nminin.bindingbuilder.default

import android.view.View
import com.nminin.bindingbuilder.Decorator

class VisibilityDecorator<V: View> : Decorator<V, Int>() {
    override fun bind(view: V, item: Int) {
        view.visibility = item
    }
}