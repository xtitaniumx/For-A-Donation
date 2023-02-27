package com.example.donate.data.storage.retrofit

import com.example.donate.data.storage.model.RetrofitModel
import retrofit2.http.GET
import retrofit2.http.Path

// Api for example
interface RetrofitApi {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): RetrofitModel
}