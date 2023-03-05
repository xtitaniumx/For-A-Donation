package com.example.donate.data.repository

import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.request.AuthFamilyRequest
import com.example.donate.domain.model.AuthFamilyParam
import com.example.donate.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage) : UserRepository {
    override fun authByEmail(param: AuthFamilyParam) {
        userStorage.auth(mapToStorage(param))
    }

    private fun mapToStorage(loginFamilyParam: AuthFamilyParam): AuthFamilyRequest {
        return AuthFamilyRequest(
            email = loginFamilyParam.email,
            password = loginFamilyParam.password
        )
    }
}