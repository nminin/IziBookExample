package com.nminin.izibookexample.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nminin.izibookexample.BuildConfig

data class Category(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("icon")
    @Expose
    val iconId: Int,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("items")
    @Expose
    val items: List<Subcategory>
) {
    fun getIconUrl(h: Int, w: Int) = "${BuildConfig.API_AUTH_URL}?&image=$id&h=$h&w=$w"
}