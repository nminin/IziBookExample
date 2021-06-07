package com.nminin.bindingbuilder.recycler

import android.view.ViewGroup

abstract class ViewHolderFactory<T, V: ViewHolder<T>> {

    abstract fun getViewType(item: T): Int

    abstract fun getViewHolder(viewType: Int, viewGroup: ViewGroup): V
}