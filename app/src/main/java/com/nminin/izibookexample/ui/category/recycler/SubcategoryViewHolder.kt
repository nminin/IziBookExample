package com.nminin.izibookexample.ui.category.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatTextView
import com.nminin.bindingbuilder.recycler.ViewHolder
import com.nminin.izibookexample.R
import com.nminin.izibookexample.data.model.Subcategory

class SubcategoryViewHolder private constructor(view: View) :
    ViewHolder<Subcategory>(view) {

    companion object {
        fun create(parent: ViewGroup, @LayoutRes layout: Int = R.layout.item_category_subcategory) =
            SubcategoryViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(layout, parent, false)
            )
    }

    override fun bind(
        item: Subcategory,
        editMode: Boolean,
        onItemClick: ((Subcategory) -> Unit)?,
        onLongItemClick: ((Subcategory) -> Unit)?,
        additionalEvents: List<Pair<Int, (Subcategory) -> Unit>>
    ) {
        super.bind(item, editMode, onItemClick, onLongItemClick, additionalEvents)
        (itemView as AppCompatTextView).text = item.title
    }

}