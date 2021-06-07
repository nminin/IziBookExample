package com.nminin.izibookexample.data.networking

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryRequest(
    @SerializedName("filter")
    val filter: Filter = Filter(),
    @SerializedName("view")
    val view: List<RequestData> = listOf(
        RequestData("icon"),
        RequestData("id"),
        RequestData("title"),
        RequestData("popularity"),
        RequestData(
            "items", listOf(
                RequestData("id"),
                RequestData("title"),
                RequestData(
                    "items", listOf(
                        RequestData(
                            "items", listOf(
                                RequestData("id"),
                                RequestData("title")
                            )
                        )
                    )
                ),
            )
        ),
    )
) {
    data class Filter(
        @SerializedName("parent")
        val parent: Int = 1
    )

    data class RequestData(
        @SerializedName("code")
        val code: String,
        @SerializedName("view")
        @Expose
        val view: List<RequestData>? = null
    )
}