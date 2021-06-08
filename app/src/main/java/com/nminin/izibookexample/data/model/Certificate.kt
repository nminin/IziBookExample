package com.nminin.izibookexample.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Certificate(
    @SerializedName("public")
    @Expose
    val public: String,
    @SerializedName("private")
    @Expose
    val private: String,
    @SerializedName("expire")
    @Expose
    val expire: String,
    @SerializedName("pkcs12")
    @Expose
    val pkcs12: String,
)