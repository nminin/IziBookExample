package com.nminin.izibookexample.data.networking

import com.nminin.izibookexample.data.model.Category
import com.nminin.izibookexample.data.model.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @POST("api/globcat/list")
    fun getCategories(@Body request: CategoryRequest = CategoryRequest()): Call<Response<List<Category>>>
}