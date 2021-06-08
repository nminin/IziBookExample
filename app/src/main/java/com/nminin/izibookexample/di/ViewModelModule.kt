package com.nminin.izibookexample.di

import com.nminin.izibookexample.ui.category.CategoryViewModel
import com.nminin.izibookexample.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CategoryViewModel(get()) }
    viewModel { SplashViewModel(get()) }
}
