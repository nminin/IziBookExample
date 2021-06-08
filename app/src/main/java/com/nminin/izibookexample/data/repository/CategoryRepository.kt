package com.nminin.izibookexample.data.repository

import com.jakewharton.rxrelay3.BehaviorRelay
import com.nminin.izibookexample.data.model.Category
import com.nminin.izibookexample.data.networking.Api
import com.ronasit.networking.singleResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoryRepository(
    private val api: Api
) {
    private val data = BehaviorRelay.createDefault<List<Category>>(listOf())

    fun get(): Observable<List<Category>> = data

    fun refresh(): Single<Unit> = api.getCategories().singleResponse()
        .observeOn(Schedulers.computation())
        .flatMap {
            it.mapData()
        }
        .doOnSuccess {
            data.accept(it)
        }
        .map {
            Unit
        }
}