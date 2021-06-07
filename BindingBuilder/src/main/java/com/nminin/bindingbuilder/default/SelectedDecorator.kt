package com.nminin.bindingbuilder.default

import android.view.View
import com.nminin.bindingbuilder.Decorator

class SelectedDecorator<V: View> : Decorator<V, Boolean>() {
    override fun bind(view: V, item: Boolean) {
        view.isSelected = item
    }
}