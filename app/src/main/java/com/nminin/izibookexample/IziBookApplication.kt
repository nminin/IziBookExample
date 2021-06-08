package com.nminin.izibookexample

import android.app.Application
import com.nminin.izibookexample.di.dataModule
import com.nminin.izibookexample.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IziBookApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            this.modules(
                listOf(
                    dataModule,
                    viewModelModule
                )
            )
            this.androidContext(this@IziBookApplication)
        }
    }
}