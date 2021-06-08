package com.nminin.izibookexample.ui.category.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nminin.bindingbuilder.bind
import com.nminin.bindingbuilder.recycler.ViewHolder
import com.nminin.izibookexample.R
import com.nminin.izibookexample.data.model.Category
import com.nminin.izibookexample.ui.category.model.ExpandableItem

class CategoryViewHolder private constructor(view: View) :
    ViewHolder<ExpandableItem<Category>>(view) {

    companion object {
        fun create(parent: ViewGroup, @LayoutRes layout: Int = R.layout.item_category_expandable) =
            CategoryViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(layout, parent, false)
            )
    }

    override fun bind(
        item: ExpandableItem<Category>,
        editMode: Boolean,
        onItemClick: ((ExpandableItem<Category>) -> Unit)?,
        onLongItemClick: ((ExpandableItem<Category>) -> Unit)?,
        additionalEvents: List<Pair<Int, (ExpandableItem<Category>) -> Unit>>
    ) {
        super.bind(item, editMode, onItemClick, onLongItemClick, additionalEvents)
        bindExpandable(item.isExpanded)
        Glide.with(itemView)
            .load(item.item.getIconUrl(40, 40))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemView.findViewById(R.id.image_icon))

        itemView.setOnClickListener {
            item.isExpanded = !item.isExpanded
            bindExpandable(item.isExpanded)
        }
        itemView.findViewById<TextView>(R.id.text_title).text = item.item.title
        itemView.findViewById<RecyclerView>(R.id.recycler_subcategory).apply {
            this.bind(SubcategoryViewHolderFactory())
                .items(item.item.items)
        }
    }

    private fun bindExpandable(isExpanded: Boolean) {
        itemView.findViewById<RecyclerView>(R.id.recycler_subcategory).visibility =
            if (isExpanded) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

}