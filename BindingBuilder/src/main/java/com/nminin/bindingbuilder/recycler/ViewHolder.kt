package com.nminin.bindingbuilder.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    open fun bind(
        item: T,
        editMode: Boolean,
        onItemClick: ((T) -> Unit)?,
        onLongItemClick: ((T) -> Unit)?,
        additionalEvents: List<Pair<Int, (T) -> Unit>>
    ) {
        itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
        itemView.setOnLongClickListener {
            onLongItemClick?.let {
                it.invoke(item)
                true
            } ?: false
        }
        initActions(additionalEvents, item)
    }

    private fun initActions(actions: List<Pair<Int, (T) -> Unit>>, item: T) {
        actions.forEach {action ->
            itemView.findViewById<View>(action.first).setOnClickListener {
                action.second.invoke(item)
            }
        }
    }
}