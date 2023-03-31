package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.AuthByPhoneRequest
import com.example.donate.data.storage.model.request.GetUserByIdRequest
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.UserResponse

interface UserStorage {
    suspend fun register(request: RegisterUserRequest): UserResponse?

    suspend fun auth(request: AuthByPhoneRequest): UserResponse?

    suspend fun get(request: GetUserByIdRequest): UserResponse?
}