package com.nminin.izibookexample.data.networking

import com.nminin.izibookexample.data.model.Certificate
import com.nminin.izibookexample.data.model.Response
import retrofit2.Call
import retrofit2.http.POST
import java.io.InputStream

interface AuthApi {
    @POST("api/anonymous")
    fun getSsl(): Call<Response<Certificate>>
}