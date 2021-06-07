package com.nminin.bindingbuilder.default

import android.widget.TextView
import com.nminin.bindingbuilder.Decorator

class TextDecorator<V: TextView>() : Decorator<V, String>() {
    override fun bind(view: V, item: String) {
        if (!view.text.toString().equals(item)) {
            view.text = item
        }
    }

}