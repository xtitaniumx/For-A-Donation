package com.example.donate.domain.usecase

import com.example.donate.domain.model.AuthByPhoneUserParam
import com.example.donate.domain.repository.UserRepository

class AuthByPhoneUseCase(private val userRepository: UserRepository) {
    private val errorMessages = ArrayList<String>()

    suspend operator fun invoke(authByPhoneUserParam: AuthByPhoneUserParam): Any? {
        errorMessages.clear()
        validatePhone(phone = authByPhoneUserParam.phoneNumber)
        validatePassword(password = authByPhoneUserParam.password)
        if (errorMessages.isNotEmpty()) {
            return errorMessages
        }
        return userRepository.authByPhone(param = authByPhoneUserParam)
    }

    private fun validatePhone(phone: String) {
        when {
            phone.isEmpty() -> {
                errorMessages.add("Введите номер телефона")
            }
        }
    }

    private fun validatePassword(password: String) {
        when {
            password.isEmpty() -> {
                errorMessages.add("Введите пароль")
            }
        }
    }
}