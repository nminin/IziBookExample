package com.nminin.bindingbuilder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nminin.bindingbuilder.recycler.RecyclerAdapterBindingBuilder
import com.nminin.bindingbuilder.recycler.ViewHolder
import com.nminin.bindingbuilder.recycler.ViewHolderFactory

fun <V : View> V.bind() = BindingBuilder(this)

fun <V : RecyclerView, T> V.bind(
    viewHolder: (parent: ViewGroup) -> ViewHolder<T>
): RecyclerAdapterBindingBuilder<T, ViewHolder<T>, ViewHolderFactory<T, ViewHolder<T>>> =
    RecyclerAdapterBindingBuilder(
        this, object : ViewHolderFactory<T, ViewHolder<T>>() {
            override fun getViewType(item: T): Int = 1

            override fun getViewHolder(viewType: Int, viewGroup: ViewGroup): ViewHolder<T> {
                return viewHolder.invoke(viewGroup)
            }
        }
    )

fun <V : RecyclerView, T, VH : ViewHolder<T>, VHF : ViewHolderFactory<T, VH>> V.bind(
    viewHolderFactory: VHF
) = RecyclerAdapterBindingBuilder(
    this, viewHolderFactory
)

fun <V : CompoundButton> BindingBuilder<V>.onChecked(predicate: (isChecked: Boolean) -> Unit) =
    this.apply {
        view.setOnCheckedChangeListener { buttonView, isChecked ->
            predicate.invoke(isChecked)
        }
    }

fun BindingBuilder<SwipeRefreshLayout>.onRefresh(action: () -> Unit) {
    view.setOnRefreshListener {
        action.invoke()
    }
}
fun BindingBuilder<EditText>.onTextChanged(textListener: (String) -> Unit) = this.apply {
    this.view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //ignore
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //ignore
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textListener.invoke(s?.toString() ?: "")
        }

    })
}