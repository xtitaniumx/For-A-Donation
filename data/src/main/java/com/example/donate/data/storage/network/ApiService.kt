package com.example.donate.data.storage.network

import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.RegisterUserResponse
import com.example.donate.data.storage.model.response.TaskResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("/api/User/Registration")
    fun registerUser(@Body request: RegisterUserRequest): Call<RegisterUserResponse>

    @GET("/api/Task/GetAll")
    fun getUserTasks(): Call<List<TaskResponse>>
}