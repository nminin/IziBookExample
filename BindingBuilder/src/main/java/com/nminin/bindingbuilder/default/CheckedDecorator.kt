package com.nminin.bindingbuilder.default

import android.widget.CompoundButton
import com.nminin.bindingbuilder.Decorator

class CheckedDecorator<V : CompoundButton> : Decorator<V, Boolean>() {
    override fun bind(view: V, item: Boolean) {
        if (view.isChecked != item) {
            view.isChecked = item
        }
    }
}