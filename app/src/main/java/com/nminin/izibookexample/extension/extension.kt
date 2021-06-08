package com.nminin.izibookexample.extension

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.dispose(disposable: CompositeDisposable) {
    disposable.add(this)
}