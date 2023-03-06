package com.example.donate.domain.usecase

import com.example.donate.domain.model.TestAuthParam
import com.example.donate.domain.model.TestUserItem
import com.example.donate.domain.repository.UserRepository

class TestAuthUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(testAuthParam: TestAuthParam): String {
        val testUser = userRepository.testAuth(param = testAuthParam)
        return testUser?.email ?: "empty"
    }
}