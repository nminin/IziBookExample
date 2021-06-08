package com.nminin.izibookexample.ui.base

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class ViewModel: ViewModel() {
    protected val disposable = CompositeDisposable()
    protected val error = PublishRelay.create<String>()

    fun getError(): Observable<String> = error

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}