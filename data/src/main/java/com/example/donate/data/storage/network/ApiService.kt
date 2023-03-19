package com.example.donate.data.storage.network

import com.example.donate.data.storage.model.request.AddTaskRequest
import com.example.donate.data.storage.model.request.AuthByPhoneRequest
import com.example.donate.data.storage.model.request.GetTaskByFilterRequest
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.FamilyResponse
import com.example.donate.data.storage.model.response.UserResponse
import com.example.donate.data.storage.model.response.TaskResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("/api/User/Registration")
    fun registerUser(@Body request: RegisterUserRequest): Call<UserResponse>

    @POST("/api/User/Authorization")
    fun authUserByPhone(@Body request: AuthByPhoneRequest): Call<UserResponse>

    @GET("/api/Task/GetAll")
    fun getAllTasks(): Call<List<TaskResponse>>

    @GET("/api/Task/GetById/{id}")
    fun getTaskById(@Path("id") id: String): Call<TaskResponse>

    @POST("/api/Task/GetByFilter")
    fun getTaskByFilter(@Body request: GetTaskByFilterRequest): Call<List<TaskResponse>>

    @POST("/api/Task/Create")
    fun addTask(@Body request: AddTaskRequest): Call<TaskResponse>

    @GET("/api/Family/GetById/{id}")
    fun getFamilyById(@Path("id") familyId: String): Call<FamilyResponse>

    @PUT("/api/Family/AddMember/{userId},{familyId}")
    fun addFamilyMember(@Path("userId") userId: String, @Path("familyId") familyId: String): Call<FamilyResponse>
}