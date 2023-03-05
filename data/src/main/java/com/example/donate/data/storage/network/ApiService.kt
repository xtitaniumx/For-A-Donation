package com.example.donate.data.storage.network

import com.example.donate.data.storage.model.request.AuthFamilyRequest
import com.example.donate.data.storage.model.response.AuthFamilyResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("auth")
    @FormUrlEncoded
    fun auth(@Body request: AuthFamilyRequest): Call<AuthFamilyResponse>
}