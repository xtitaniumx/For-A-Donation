package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.UserResponse

interface UserStorage {
    suspend fun register(param: RegisterUserRequest): UserResponse

    fun auth(): Boolean
}