package com.nminin.izibookexample.data.model

import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Single

data class Response<T>(
    @SerializedName("result")
    @Expose
    val result: Result,
    @SerializedName("data")
    @Expose
    val data: T? = null,
    @SerializedName("reason")
    @Expose
    val reason: String? = null,

) {
    enum class Result {
        @SerializedName("ok")
        OK,
        @SerializedName("error")
        ERROR
    }

    fun mapData() = Single.create<T> {  emitter ->
        if (result == Result.OK && this.data != null) {
            emitter.onSuccess(this.data)
        } else {
            this.reason?.let {
                emitter.onError(Throwable(it))
            } ?: kotlin.run {
                emitter.onError(Throwable("Нет связи с сервером"))
            }
        }
    }
}