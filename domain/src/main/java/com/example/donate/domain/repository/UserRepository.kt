package com.example.donate.domain.repository

import com.example.donate.domain.model.*

interface UserRepository {
    suspend fun registerUser(param: RegisterUserParam): UserItem?

    fun authByRememberData(): Boolean

    suspend fun authByPhone(param: AuthByPhoneUserParam): UserItem?

    suspend fun getUserById(param: GetUserByIdParam): UserItem?

    fun getUserId(): String?

    fun getFamilyId(): String?
}