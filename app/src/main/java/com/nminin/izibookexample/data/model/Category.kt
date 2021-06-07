package com.nminin.izibookexample.data.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random
import kotlin.random.nextInt

data class Category(
    @SerializedName("id")
    private val id: Int,
    @SerializedName("icon")
    private val iconId: Int,
    @SerializedName("title")
    private val title: String,
    @SerializedName("items")
    private val items: List<Subcategory>
)