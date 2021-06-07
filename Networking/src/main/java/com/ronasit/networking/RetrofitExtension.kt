package com.ronasit.networking

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sun.rmi.runtime.Log

fun <T, E> Call<T>.responseCustomError(
    onSuccess: (Int, T) -> Unit,
    onError: (Int, E?) -> Unit,
    onSuccessEmpty: ((Int) -> Unit)? = null,
    errorClass: Class<E>
) {
    this.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>?, response: Response<T>?) {

            response?.let {
                if (response.code() / 100 == 2) {
                    response.body()?.let {
                        onSuccess.invoke(
                            response.code(),
                            it
                        )
                    } ?: run {
                        onSuccessEmpty?.let {
                            onSuccessEmpty.invoke(response.code())
                        } ?: kotlin.run {
                            onError.invoke(response.code(), null)
                        }
                    }
                } else {
                    response.errorBody()?.let {
                        try {
                            onError.invoke(
                                response.code(),
                                Gson().fromJson(
                                    it.string(),
                                    errorClass
                                )
                            )
                        } catch (e: IllegalStateException) {
                            it.string()
                        } catch (e: JsonSyntaxException) {
                            it.string()
                        } catch (e: NullPointerException) {
                            it.string()
                        }
                    }
                }
            } ?: run {
                onError.invoke(0, null)
            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            onError.invoke(0, null)
        }
    })
}

fun <T> Call<T>.response(
    onSuccess: (Int, T) -> Unit,
    onError: (Int, String) -> Unit,
    onSuccessEmpty: ((Int) -> Unit)? = null,
    errorDeserializer: ((Int, ResponseBody?) -> String)? = null
) {

    this.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>?, response: Response<T>?) {

            response?.let {
                if (response.code() / 100 == 2) {
                    response.body()?.let {
                        onSuccess.invoke(
                            response.code(),
                            it
                        )
                    } ?: run {
                        onSuccessEmpty?.let {
                            onSuccessEmpty.invoke(response.code())
                        } ?: kotlin.run {
                            onError.invoke(response.code(), "Empty body.")
                        }
                    }
                } else {
                    val message = errorDeserializer?.let {
                        it.invoke(response.code(), response.errorBody())
                    } ?: response.errorBody()?.let {
                        try {
                            Gson().fromJson(
                                it.string(),
                                ErrorResponse::class.java
                            )
                                .message()
                        } catch (e: IllegalStateException) {
                            it.string()
                        } catch (e: JsonSyntaxException) {
                            it.string()
                        } catch (e: NullPointerException) {
                            it.string()
                        }
                    } ?: "Empty error message"
                    onError.invoke(response.code(), message)
                }
            } ?: run {
                onError.invoke(0, "Empty response")
            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            onError.invoke(0, t?.localizedMessage ?: "Request error")
        }
    })
}

class ErrorResponse {
    @SerializedName("message")
    @Expose
    var message: String = ""
    @SerializedName("Message")
    @Expose
    var fixelMessage: String = ""

    @SerializedName("error")
    @Expose
    var error: String = ""

    fun message(): String {
        return when {
            fixelMessage.isNotBlank() -> fixelMessage
            message.isNotBlank() -> message
            error.isNotBlank() -> error
            else -> "Unknown Error"
        }
    }
}