package com.example.donate.domain.repository

import com.example.donate.domain.model.AuthFamilyParam
import com.example.donate.domain.model.TestAuthParam
import com.example.donate.domain.model.TestUserItem

interface UserRepository {
    fun authByEmail(param: AuthFamilyParam)

    suspend fun testAuth(param: TestAuthParam): TestUserItem?
}