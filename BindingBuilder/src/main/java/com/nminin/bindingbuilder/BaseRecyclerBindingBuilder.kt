package com.nminin.bindingbuilder

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.nminin.bindingbuilder.recycler.DiffUtilCallback
import com.nminin.bindingbuilder.recycler.RecyclerAdapter
import com.nminin.bindingbuilder.recycler.ViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseRecyclerBindingBuilder<T, V : ViewHolder<T>, R : RecyclerAdapter<T, V>>(
    private val view: RecyclerView
) : LifecycleObserver {
    private val disposable = CompositeDisposable()
    abstract var adapter: R

    init {
        view.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View?) {
                //ignore
            }

            override fun onViewDetachedFromWindow(p0: View?) {
                disposable.dispose()
            }

        })
        view.adapter = adapter
    }

    fun <A> observe(observable: Observable<A>, decorator: Decorator<RecyclerView, A>) =
        this.apply {
            disposable.add(
                observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        decorator.bind(view, it)
                    }, {

                    })
            )
        }

    fun setDiffUtilCallback(diffUtilCallback: DiffUtilCallback<T>) = this.apply {
        this.adapter.setDiffUtilCallback(diffUtilCallback)
    }

    fun editMode(observable: Observable<Boolean>) = this.apply {
        disposable.add(
            observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    safeAdapter().setEditMode(it)
                }, {

                })
        )
    }

    fun observeItems(items: Observable<List<T>>, onItemsUpdatedCallback: ((items: List<T>) -> Unit)? = null) =
        this.apply {
            items.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    safeAdapter().updateData(it, onItemsUpdatedCallback)
                }, {
                    //ignore
                })
        }

    fun <T : RecyclerView.LayoutManager> layoutManager(layoutManager: T) = this.apply {
        this.view.layoutManager = layoutManager
    }

    fun items(value: List<T>) = this.apply {
        safeAdapter().updateData(value)
    }

    fun onItemClick(id: Int? = null, onClick: (item: T) -> Unit) = this.apply {
        if (id != null) {
            safeAdapter().addAction(id, onClick)
        } else {
            safeAdapter().onItemClick = onClick
        }
    }

    fun onLongItemClick(onClick: (item: T) -> Unit) = this.apply {
        safeAdapter().onLongItemClick = onClick
    }

    fun onAction(id: Int, action: (item: T) -> Unit) = this.apply {
        safeAdapter().addAction(id, action)
    }

    protected fun safeAdapter(): R = if (view.adapter == null) {
        view.adapter = adapter
        adapter
    } else {
        adapter
    }
}