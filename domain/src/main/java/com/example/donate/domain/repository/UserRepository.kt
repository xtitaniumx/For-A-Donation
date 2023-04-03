package com.example.donate.domain.repository

import com.example.donate.domain.model.*

interface UserRepository {
    suspend fun registerAccount(param: AccountRegisterParam): UserItem?

    fun authByRememberData(): Boolean

    suspend fun authByPhone(param: AuthByPhoneUserParam): UserItem?

    fun logOutAccount()

    suspend fun getUserById(param: GetUserByIdParam): UserItem?

    fun getUserId(): String?

    fun getFamilyId(): String?
}