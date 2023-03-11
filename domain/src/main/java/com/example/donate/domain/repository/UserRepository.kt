package com.example.donate.domain.repository

import com.example.donate.domain.model.*

interface UserRepository {
    suspend fun registerUser(param: RegisterUserParam): UserItem

    fun authByToken(): Boolean
}