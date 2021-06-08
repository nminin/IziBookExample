package com.nminin.izibookexample.ui.splash

import android.util.Log
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import com.nminin.izibookexample.data.repository.CertificateRepository
import com.nminin.izibookexample.extension.dispose
import com.nminin.izibookexample.ui.base.ViewModel
import io.reactivex.rxjava3.core.Observable

class SplashViewModel(certificateRepository: CertificateRepository): ViewModel() {

    private val isSuccess = BehaviorRelay.createDefault(PreloadProgress.PRELOADING)

    init {
        certificateRepository.refresh()
            .subscribe({
                isSuccess.accept(PreloadProgress.SUCCESS)
            }, {
                isSuccess.accept(PreloadProgress.ERROR)
                error.accept(it.localizedMessage)
            })
            .dispose(disposable)
    }

    fun isSuccess(): Observable<PreloadProgress> = isSuccess

    enum class PreloadProgress {
        PRELOADING,
        SUCCESS,
        ERROR
    }
}