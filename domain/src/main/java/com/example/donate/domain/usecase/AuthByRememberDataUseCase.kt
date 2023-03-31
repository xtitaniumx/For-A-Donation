package com.example.donate.domain.usecase

import com.example.donate.domain.repository.UserRepository

class AuthByRememberDataUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Boolean {
        return userRepository.authByRememberData()
    }
}