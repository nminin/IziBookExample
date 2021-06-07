package com.nminin.izibookexample.data.model

import com.google.gson.annotations.SerializedName

data class Subcategory (
    @SerializedName("id")
    private val id: Int,
    @SerializedName("title")
    private val title: String
)