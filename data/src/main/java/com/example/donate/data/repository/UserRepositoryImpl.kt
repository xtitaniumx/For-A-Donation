package com.example.donate.data.repository

import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.request.AuthFamilyRequest
import com.example.donate.data.storage.model.request.TestAuthRequest
import com.example.donate.data.storage.model.response.TestAuthResponse
import com.example.donate.domain.model.AuthFamilyParam
import com.example.donate.domain.model.TestAuthParam
import com.example.donate.domain.model.TestUserItem
import com.example.donate.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage) : UserRepository {
    override fun authByPhone(param: AuthFamilyParam) {
        userStorage.auth(mapToStorage(param))
    }

    override suspend fun testAuth(param: TestAuthParam): TestUserItem? {
        val testUser = userStorage.authTest(mapToStorage(param))
        return testUser?.let { mapToDomain(it) }
    }

    private fun mapToStorage(loginFamilyParam: AuthFamilyParam): AuthFamilyRequest {
        return AuthFamilyRequest(
            phone = loginFamilyParam.phone,
            password = loginFamilyParam.password
        )
    }

    private fun mapToStorage(testAuthParam: TestAuthParam): TestAuthRequest {
        return TestAuthRequest(
            username = testAuthParam.username,
            password = testAuthParam.password
        )
    }

    private fun mapToDomain(testAuthResponse: TestAuthResponse): TestUserItem {
        return TestUserItem(
            id = testAuthResponse.id,
            username = testAuthResponse.username,
            email = testAuthResponse.email,
            firstName = testAuthResponse.firstName,
            lastName = testAuthResponse.lastName,
            gender = testAuthResponse.gender,
            image = testAuthResponse.image,
            token = testAuthResponse.token
        )
    }
}