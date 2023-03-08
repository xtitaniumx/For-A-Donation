package com.example.donate.domain.usecase

import com.example.donate.domain.model.AuthFamilyParam
import com.example.donate.domain.repository.UserRepository

class AuthByPhoneUseCase(private val userRepository: UserRepository) {
    operator fun invoke(loginUserParam: AuthFamilyParam) {
        return userRepository.authByPhone(param = loginUserParam)
    }
}