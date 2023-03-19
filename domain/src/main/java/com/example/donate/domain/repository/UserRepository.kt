package com.example.donate.domain.repository

import com.example.donate.domain.model.*

interface UserRepository {
    suspend fun registerUser(param: RegisterUserParam): UserItem?

    fun authByToken(): Boolean

    suspend fun authByPhone(param: AuthByPhoneUserParam): UserItem?

    fun authBySavedData(): Boolean

    fun getUserId(): String?

    fun getFamilyId(): String?
}