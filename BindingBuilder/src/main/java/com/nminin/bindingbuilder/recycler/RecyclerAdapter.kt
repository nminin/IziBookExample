package com.nminin.bindingbuilder.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class RecyclerAdapter<T, V : ViewHolder<T>>(
    private val viewHolderFactory: ViewHolderFactory<T,V>
) : RecyclerView.Adapter<V>() {

    protected var items: List<T> = mutableListOf()
    protected var actions = mutableListOf<Pair<Int, (T) -> Unit>>()
    private var editMode: Boolean = false
    private var diffUtil: DiffUtilCallback<T>? = null

    var onItemClick: ((T) -> Unit)? = null
    var onLongItemClick: ((T) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V =
        viewHolderFactory.getViewHolder(viewType, parent)

    override fun getItemCount(): Int {
        return items.size
    }

    fun setDiffUtilCallback(diffUtilCallback: DiffUtilCallback<T>) {
        diffUtil = diffUtilCallback
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        items.get(position)?.let {
            holder.bind(it, editMode, onItemClick, onLongItemClick, actions)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewHolderFactory.getViewType(items[position])
    }

    open fun updateData(items: List<T>?, onItemsUpdatedCallback: ((items: List<T>) -> Unit)? = null) {
        diffUtil?.let {
            it.setItems(this.items, items ?: emptyList())
            DiffUtil.calculateDiff(it).let {
                this.items = items ?: listOf<T>()
                it.dispatchUpdatesTo(this)
            }
        } ?: kotlin.run {
            this.items = items ?: listOf()
            notifyDataSetChanged()
        }
        onItemsUpdatedCallback?.invoke(this.items)
    }

    fun addAction(id: Int, action: (T) -> Unit) {
        actions.add(id to action)
    }

    fun setEditMode(value: Boolean) {
        if (editMode != value) {
            editMode = !editMode
            notifyDataSetChanged()
        }
    }
}