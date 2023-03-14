package com.example.donate.data.storage.network

import com.example.donate.data.storage.model.request.AddTaskRequest
import com.example.donate.data.storage.model.request.AuthByPhoneRequest
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.FamilyResponse
import com.example.donate.data.storage.model.response.UserResponse
import com.example.donate.data.storage.model.response.TaskResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/api/User/Registration")
    fun registerUser(@Body request: RegisterUserRequest): Call<UserResponse>

    @POST("/api/User/Authorization")
    fun authUserByPhone(@Body request: AuthByPhoneRequest): Call<UserResponse>

    @GET("/api/Task/GetAll")
    fun getUserTasks(): Call<List<TaskResponse>>

    @POST("/api/Task/Create")
    fun addTask(@Body request: AddTaskRequest): Call<TaskResponse>

    @GET("/api/Family/GetById/{id}")
    fun getFamilyById(@Path("id") familyId: String): Call<FamilyResponse>
}