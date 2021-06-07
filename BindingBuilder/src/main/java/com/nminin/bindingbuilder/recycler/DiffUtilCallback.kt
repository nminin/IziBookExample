package com.nminin.bindingbuilder.recycler

import androidx.recyclerview.widget.DiffUtil

abstract class DiffUtilCallback<T>: DiffUtil.Callback() {
    lateinit var oldItems: List<T>
    lateinit var newItems: List<T>

    fun setItems(oldItems: List<T>, newItems: List<T>) {
        this.oldItems = oldItems
        this.newItems = newItems
    }

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(
            oldItems[oldItemPosition],
            newItems[newItemPosition]
        )
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(
            oldItems[oldItemPosition],
            newItems[newItemPosition]
        )
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size
}