package com.nminin.izibookexample.ui.category

import android.util.Log
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import com.nminin.izibookexample.data.model.Category
import com.nminin.izibookexample.data.repository.CategoryRepository
import com.nminin.izibookexample.extension.dispose
import com.nminin.izibookexample.ui.base.ViewModel
import com.nminin.izibookexample.ui.category.model.ExpandableItem
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoryViewModel(categoryRepository: CategoryRepository) : ViewModel() {

    private val categories = BehaviorRelay.createDefault<List<ExpandableItem<Category>>>(emptyList())

    init {
        categoryRepository.refresh()
            .observeOn(Schedulers.computation())
            .subscribe({
                //ignore
            }, {
                Log.d("asdasdasd", it.localizedMessage)
                it.printStackTrace()
                error.accept(it.localizedMessage)
            })
            .dispose(disposable)
        categoryRepository.get()
            .observeOn(Schedulers.computation())
            .map {
                it.map {
                    ExpandableItem(false, it)
                }
            }
            .subscribe {
                categories.accept(it)
            }
            .dispose(disposable)
    }

    fun getCategories() = categories

}