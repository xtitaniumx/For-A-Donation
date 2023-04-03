package com.example.donate.domain.usecase

import com.example.donate.domain.repository.UserRepository

class LogOutAccountUseCase(private val userRepository: UserRepository) {
    operator fun invoke() {
        userRepository.logOutAccount()
    }
}