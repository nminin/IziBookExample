package com.nminin.izibookexample.data.model

import com.google.gson.annotations.SerializedName

data class Certificate(
    @SerializedName("public")
    val public: String,
    @SerializedName("private")
    val private: String,
    @SerializedName("expire")
    val expire: Long,
    @SerializedName("pkcs12")
    val pkcs12: String,
)