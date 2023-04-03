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
    override suspend fun registerAccount(param: AccountRegisterParam): UserItem? {
        val user = userStorage.register(mapToStorage(registerUserParam = param))
        return user?.let { mapToDomain(userResponse = it) }
    }

    override fun authByRememberData(): Boolean {
        val tokenIsExist = !userDataStorage.fetchData(PrefDataConstants.USER_TOKEN).isNullOrEmpty()
        val loginState = userDataStorage.fetchData(PrefDataConstants.USER_LOGGED_IN).toBoolean()

        return tokenIsExist && loginState
    }

    override suspend fun authByPhone(param: AuthByPhoneUserParam): UserItem? {
        val user = userStorage.auth(mapToStorage(authByPhoneUserParam = param))
        return user?.let { mapToDomain(userResponse = it) }
    }

    override fun logOutAccount() {
        userDataStorage.removeAllData()
    }

    override suspend fun getUserById(param: GetUserByIdParam): UserItem? {
        val user = userStorage.get(mapToStorage(getUserByIdParam = param))
        return user?.let { mapToDomain(userResponse = it) }
    }

    override fun getUserId(): String? {
        return userDataStorage.fetchData(PrefDataConstants.USER_ID)
    }

    override fun getFamilyId(): String? {
        return userDataStorage.fetchData(PrefDataConstants.FAMILY_ID)
    }

    private fun mapToStorage(authByPhoneUserParam: AuthByPhoneUserParam): AuthByPhoneRequest {
        return AuthByPhoneRequest(
            phoneNumber = authByPhoneUserParam.phoneNumber,
            password = authByPhoneUserParam.password,
            remember = authByPhoneUserParam.remember
        )
    }

    private fun mapToStorage(registerUserParam: AccountRegisterParam): RegisterUserRequest {
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