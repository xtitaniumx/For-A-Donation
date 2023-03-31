package com.example.donate.data.repository

import com.example.donate.data.storage.UserDataStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.PrefDataConstants
import com.example.donate.data.storage.model.request.AuthByPhoneRequest
import com.example.donate.data.storage.model.request.GetUserByIdRequest
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.UserResponse
import com.example.donate.domain.model.*
import com.example.donate.domain.repository.UserRepository

class UserRepositoryImpl(private val userStorage: UserStorage, private val userDataStorage: UserDataStorage) : UserRepository {
    override suspend fun registerUser(param: RegisterUserParam): UserItem? {
        val user = userStorage.register(mapToStorage(registerUserParam = param))
        return user?.let { mapToDomain(userResponse = it) }
    }

    override fun authByRememberData(): Boolean {
        userDataStorage.setDataId(PrefDataConstants.USER_TOKEN)
        val tokenIsExist = !userDataStorage.fetchData().isNullOrEmpty()
        userDataStorage.setDataId(PrefDataConstants.USER_LOGGED_IN)
        val loginState = userDataStorage.fetchData().toBoolean()

        return tokenIsExist && loginState
    }

    override suspend fun authByPhone(param: AuthByPhoneUserParam): UserItem? {
        val user = userStorage.auth(mapToStorage(authByPhoneUserParam = param))
        return user?.let { mapToDomain(userResponse = it) }
    }

    override suspend fun getUserById(param: GetUserByIdParam): UserItem? {
        val user = userStorage.get(mapToStorage(getUserByIdParam = param))
        return user?.let { mapToDomain(userResponse = it) }
    }

    override fun getUserId(): String? {
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
            password = authByPhoneUserParam.password,
            remember = authByPhoneUserParam.remember
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

    private fun mapToStorage(getUserByIdParam: GetUserByIdParam): GetUserByIdRequest {
        return GetUserByIdRequest(id = getUserByIdParam.id)
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