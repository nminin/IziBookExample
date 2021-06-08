package com.nminin.izibookexample.data.repository

import com.jakewharton.rxrelay3.BehaviorRelay
import com.nminin.izibookexample.data.model.Certificate
import com.nminin.izibookexample.data.networking.AuthApi
import com.ronasit.networking.singleResponse
import io.reactivex.rxjava3.core.Observable

class CertificateRepository(private val api: AuthApi) {
    private val certificate = BehaviorRelay.create<Certificate>()

    fun get(): Observable<Certificate> = certificate

    fun refresh() = api.getSsl().singleResponse()
        .flatMap {
            it.mapData()
        }
        .doOnSuccess {
            certificate.accept(it)
        }
        .map {
            Unit
        }
}