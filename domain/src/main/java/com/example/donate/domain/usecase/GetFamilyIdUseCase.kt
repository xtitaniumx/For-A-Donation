package com.example.donate.domain.usecase

import com.example.donate.domain.repository.UserRepository

class GetFamilyIdUseCase(private val userRepository: UserRepository) {
    operator fun invoke(): String? {
        return userRepository.getFamilyId()
    }
}