package com.nminin.bindingbuilder


import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BindingBuilder<V : View>(
    val view: V
) : LifecycleObserver {

    private var disposable = CompositeDisposable()

    init {
        view.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View?) {
                //ignore
            }

            override fun onViewDetachedFromWindow(p0: View?) {
                disposable.dispose()
            }

        })
    }

    fun <A> observe(observable: Observable<A>, decorator: Decorator<V, A>) = this.apply{
        disposable.add(observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                decorator.bind(view, it)
            }, {
                Log.e("Binding", "Binding exception ${it.localizedMessage}")
            })
        )
    }

    fun onClick(action: ()-> Unit) = this.apply{
        view.setOnClickListener {
            action.invoke()
        }
    }
}