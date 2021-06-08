package com.nminin.izibookexample.ui.category.recycler

import android.view.ViewGroup
import com.nminin.bindingbuilder.recycler.ViewHolder
import com.nminin.bindingbuilder.recycler.ViewHolderFactory
import com.nminin.izibookexample.data.model.Category
import com.nminin.izibookexample.ui.category.model.ExpandableItem
import com.nminin.izibookexample.ui.category.recycler.CategoryViewHolder

class CategoryViewHolderFactory :
    ViewHolderFactory<ExpandableItem<Category>, ViewHolder<ExpandableItem<Category>>>() {
    override fun getViewType(item: ExpandableItem<Category>): Int = 0

    override fun getViewHolder(
        viewType: Int,
        viewGroup: ViewGroup
    ): ViewHolder<ExpandableItem<Category>> = CategoryViewHolder.create(viewGroup)


}