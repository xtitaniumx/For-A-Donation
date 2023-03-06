package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.AuthFamilyRequest
import com.example.donate.data.storage.model.request.TestAuthRequest
import com.example.donate.data.storage.model.response.TestAuthResponse

interface UserStorage {
    fun auth(param: AuthFamilyRequest)

    suspend fun authTest(param: TestAuthRequest): TestAuthResponse?
}