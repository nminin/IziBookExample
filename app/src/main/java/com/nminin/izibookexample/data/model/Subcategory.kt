package com.nminin.izibookexample.data.model

import com.google.gson.annotations.SerializedName

data class Subcategory (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)