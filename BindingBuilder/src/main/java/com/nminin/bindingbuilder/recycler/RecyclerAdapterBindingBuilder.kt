package com.nminin.bindingbuilder.recycler

import androidx.recyclerview.widget.RecyclerView
import com.nminin.bindingbuilder.BaseRecyclerBindingBuilder

class RecyclerAdapterBindingBuilder<T, VH : ViewHolder<T>, VHF : ViewHolderFactory<T, VH>>(
    private val view: RecyclerView,
    val viewHolderFactory: VHF
) : BaseRecyclerBindingBuilder<T, VH, RecyclerAdapter<T, VH>>(view) {
    override var adapter: RecyclerAdapter<T, VH> = RecyclerAdapter(viewHolderFactory)
}