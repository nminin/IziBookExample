package com.nminin.izibookexample.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("result")
    val result: Result,
    @SerializedName("result")
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
}