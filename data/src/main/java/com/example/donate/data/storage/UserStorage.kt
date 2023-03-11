package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.RegisterUserResponse

interface UserStorage {
    suspend fun register(param: RegisterUserRequest): RegisterUserResponse

    fun auth(): Boolean
}