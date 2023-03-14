package com.example.donate.data.repository

import com.example.donate.data.storage.UserDataStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.PrefDataConstants
import com.example.donate.data.storage.model.request.AuthByPhoneRequest
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.UserResponse
import com.example.donate.domain.model.AuthByPhoneUserParam
import com.example.donate.domain.model.RegisterUserParam
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.model.UserProgressItem
import com.example.donate.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage, private val userDataStorage: UserDataStorage) : UserRepository {
    override suspend fun registerUser(param: RegisterUserParam): UserItem? {
        val user = userStorage.register(mapToStorage(registerUserParam = param))
        return user?.let { mapToDomain(userResponse = it) }
    }

    override fun authByToken(): Boolean {
        return userStorage.auth()
    }

    override suspend fun authByPhone(param: AuthByPhoneUserParam): UserItem? {
        val user = userStorage.auth(mapToStorage(authByPhoneUserParam = param))
        return user?.let { mapToDomain(userResponse = it) }
    }

    override fun getId(): String? {
        userDataStorage.setDataId(PrefDataConstants.USER_ID)
        return userDataStorage.fetchData()
    }

    override fun getFamilyId(): String? {
        userDataStorage.setDataId(PrefDataConstants.FAMILY_ID)
        return userDataStorage.fetchData()
    }

    private fun mapToStorage(authByPhoneUserParam: AuthByPhoneUserParam): AuthByPhoneRequest {
        return AuthByPhoneRequest(
            phoneNumber = authByPhoneUserParam.phoneNumber,
            password = authByPhoneUserParam.password
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

    private fun mapToDomain(userResponse: UserResponse): UserItem {
        val userProgressList = ArrayList<UserProgressItem>()
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
}