package com.ronasit.networking

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PageResponse<T> {
    @SerializedName("current_page")
    @Expose
    var page: Int = 1
    @SerializedName("last_page")
    @Expose
    var lastPage: Int = 1
    @SerializedName("data")
    @Expose
    var data: List<T> = listOf()
}