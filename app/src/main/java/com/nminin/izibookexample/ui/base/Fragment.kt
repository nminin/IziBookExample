package com.nminin.izibookexample.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.nminin.izibookexample.extension.dispose
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class Fragment(@LayoutRes layout: Int): Fragment(layout) {

    protected lateinit var disposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        disposable = CompositeDisposable()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun observeErrors(viewModel: ViewModel) {
        viewModel.getError()
            .subscribe {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            .dispose(disposable)
    }
    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}