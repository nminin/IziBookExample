package com.nminin.izibookexample.ui.category.recycler

import android.view.ViewGroup
import com.nminin.bindingbuilder.recycler.ViewHolder
import com.nminin.bindingbuilder.recycler.ViewHolderFactory
import com.nminin.izibookexample.data.model.Subcategory
import com.nminin.izibookexample.ui.category.recycler.SubcategoryViewHolder

class SubcategoryViewHolderFactory :
    ViewHolderFactory<Subcategory, ViewHolder<Subcategory>>() {
    override fun getViewType(item: Subcategory): Int = 0

    override fun getViewHolder(
        viewType: Int,
        viewGroup: ViewGroup
    ): ViewHolder<Subcategory> = SubcategoryViewHolder.create(viewGroup)


}