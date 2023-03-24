package com.example.donate.domain.usecase

import com.example.donate.domain.model.GetUserByIdParam
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.repository.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(getUserByIdParam: GetUserByIdParam): UserItem? {
        return userRepository.getUserById(param = getUserByIdParam)
    }
}