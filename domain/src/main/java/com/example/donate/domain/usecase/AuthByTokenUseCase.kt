package com.example.donate.domain.usecase

import com.example.donate.domain.repository.UserRepository

class AuthByTokenUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): Boolean {
        return userRepository.authByToken()
    }
}