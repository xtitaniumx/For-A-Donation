package com.example.donate.domain.usecase

import com.example.donate.domain.model.RegisterUserParam
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.repository.UserRepository

class RegisterUserUseCase(private val userRepository: UserRepository) {
    private val errorMessages = ArrayList<String>()

    suspend operator fun invoke(registerUserParam: RegisterUserParam): Any {
        errorMessages.clear()
        validateName(registerUserParam.name)
        validatePassword(registerUserParam.password, registerUserParam.passwordConfirm)
        if (errorMessages.isNotEmpty()) {
            return errorMessages
        }
        return userRepository.registerUser(param = registerUserParam)
    }

    private fun validateName(name: String) {
        when {
            name.isEmpty() -> {
                errorMessages.add("Введите имя")
            }
        }
    }

    private fun validatePassword(password: String, passwordConfirm: String) {
        when {
            password != passwordConfirm -> {
                errorMessages.add("Пароли не совпадают")
            }
            password.isEmpty() -> {
                errorMessages.add("Введите пароль")
            }
            passwordConfirm.isEmpty() -> {
                errorMessages.add("Подтвердите пароль")
            }
        }
    }
}