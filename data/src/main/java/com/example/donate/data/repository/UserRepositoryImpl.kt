package com.example.donate.data.repository

import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.UserResponse
import com.example.donate.domain.model.RegisterUserParam
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.model.UserProgressItem
import com.example.donate.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage) : UserRepository {
    override suspend fun registerUser(param: RegisterUserParam): UserItem {
        val user = userStorage.register(mapToStorage(registerUserParam = param))
        return mapToDomain(userResponse = user)
    }

    override fun authByToken(): Boolean {
        return userStorage.auth()
    }

    private fun mapToDomain(userResponse: UserResponse): UserItem {
        val userProgressList = java.util.ArrayList<UserProgressItem>()
        userResponse.progress.forEach {
            userProgressList.add(UserProgressItem(
                id = it.id,
                points = it.points,
                categoryOfTask = it.categoryOfTask
            ))
        }

        return UserItem(
            id = userResponse.id,
            name = userResponse.name,
            phoneNumber = userResponse.phoneNumber,
            gender = userResponse.gender,
            role = userResponse.role,
            familyId = userResponse.familyId,
            progress = userProgressList
        )
    }

    private fun mapToStorage(registerUserParam: RegisterUserParam): RegisterUserRequest {
        return RegisterUserRequest(
            name = registerUserParam.name,
            phoneNumber = registerUserParam.phoneNumber,
            password = registerUserParam.password,
            passwordConfirm = registerUserParam.passwordConfirm,
            gender = registerUserParam.gender,
            role = registerUserParam.role,
            familyId = registerUserParam.familyId
        )
    }
}