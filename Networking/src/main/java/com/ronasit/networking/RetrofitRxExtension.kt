package com.ronasit.networking

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call


fun <T> Call<T>.singleResponse(): Single<T> = Single.create { emitter ->
    this.response(
        onSuccess = { code, item ->
            if (!emitter.isDisposed) {
                emitter.onSuccess(item)
            }
        },
        onError = { code, message ->
            if (!emitter.isDisposed) {
                emitter.onError(Exception(message))
            }
        },
        onSuccessEmpty = {
            emitter.onSuccess(null)
        })
}

fun <T,E> Call<T>.singleResponseCustomError(clazz: Class<E>): Single<Pair<T?,E?>> = Single.create { emitter ->
    this.responseCustomError(
        onSuccess = { code, item ->
            if (!emitter.isDisposed) {
                emitter.onSuccess(item to null)
            }
        },
        onSuccessEmpty = {
            if (!emitter.isDisposed) {
                emitter.onSuccess(null to null)
            }
        },
        onError = { code, message ->
            if (!emitter.isDisposed) {
                emitter.onSuccess(null to message)
            }
        },
    errorClass = clazz)
}

fun <T> Call<T>.singleResponseCode(): Single<Int> = Single.create { emitter ->
    this.response(
        onSuccess = { code, item ->
            if (!emitter.isDisposed) {
                emitter.onSuccess(code)
            }
        },
        onSuccessEmpty = {
            if (!emitter.isDisposed) {
                emitter.onSuccess(it)
            }
        },
        onError = { code, message ->
            if (!emitter.isDisposed) {
                emitter.onError(Exception(message))
            }
        })
}

fun <T, API> API.singlePagesResponse(apiCall: (api: API, page: Int) -> Call<PageResponse<T>>): Single<MutableList<T>> =
    Observable.create<List<T>> { emitter ->
        this.recursiveApiCall(apiCall, 1,
            onNext = {
                emitter.onNext(it)
            },
            onComplete = {
                emitter.onComplete()
            },
            onError = {
                emitter.onError(Exception(it))
            })
    }
        .collectInto(mutableListOf()) { list, result ->
            list.addAll(result)
        }


private fun <T, API> API.recursiveApiCall(
    apiCall: (api: API, page: Int) -> Call<PageResponse<T>>,
    page: Int,
    onNext: (List<T>) -> Unit,
    onComplete: () -> Unit,
    onError: (String) -> Unit
) {
    apiCall.invoke(this, page).response(
        onSuccess = { code, pageData ->
            onNext.invoke(pageData.data)
            if (pageData.page < pageData.lastPage) {
                this.recursiveApiCall(apiCall, page + 1, onNext, onComplete, onError)
            } else {
                onComplete.invoke()
            }
        },
        onError = { code, error ->
            onError.invoke(error)
        }
    )
}