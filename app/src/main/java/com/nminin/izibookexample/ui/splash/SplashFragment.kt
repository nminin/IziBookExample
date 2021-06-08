package com.nminin.izibookexample.ui.splash

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.nminin.bindingbuilder.bind
import com.nminin.bindingbuilder.default.VisibilityDecorator
import com.nminin.izibookexample.R
import com.nminin.izibookexample.extension.dispose
import com.nminin.izibookexample.ui.base.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val viewModel by viewModel<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeErrors(viewModel)
        view.findViewById<ProgressBar>(R.id.progress_bar)
            .bind()
            .observe(viewModel.isSuccess().map {
                if (it == SplashViewModel.PreloadProgress.PRELOADING) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
            }, VisibilityDecorator())

        viewModel.isSuccess()
            .subscribe {
                if (it == SplashViewModel.PreloadProgress.SUCCESS) {
                    findNavController().navigate(R.id.action_splashFragment_to_categoryListFragment)
                }
            }
            .dispose(disposable)
    }

}