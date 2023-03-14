package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.AuthByPhoneRequest
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.UserResponse

interface UserStorage {
    suspend fun register(request: RegisterUserRequest): UserResponse?

    fun auth(): Boolean

    suspend fun auth(request: AuthByPhoneRequest): UserResponse?
}