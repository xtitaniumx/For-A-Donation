package com.example.donate.data.storage.network

import android.content.Context
import android.util.Base64
import com.example.donate.data.storage.UserDataStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.PrefDataConstants
import com.example.donate.data.storage.model.request.AuthByPhoneRequest
import com.example.donate.data.storage.model.request.GetUserByIdRequest
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.UserResponse
import java.nio.charset.StandardCharsets

class NetworkUserStorage(context: Context, apiClient: ApiClient, private val userDataStorage: UserDataStorage) : UserStorage {
    private val apiService = apiClient.getApiService(context)

    override suspend fun register(request: RegisterUserRequest): UserResponse? {
        val response = apiService.registerUser(request).execute()
        if (response.isSuccessful) {
            response.body()?.let {
                saveUserData(
                    login = request.phoneNumber,
                    password = request.password,
                    userResponse = it
                )
            }
        }
        return response.body()
    }

    override suspend fun auth(request: AuthByPhoneRequest): UserResponse? {
        val response = apiService.authUserByPhone(request).execute()
        if (response.isSuccessful) {
            response.body()?.let {
                userDataStorage.saveData(request.remember.toString(), PrefDataConstants.USER_LOGGED_IN)
                saveUserData(
                    login = request.phoneNumber,
                    password = request.password,
                    userResponse = it
                )
            }
        }
        return response.body()
    }

    override suspend fun get(request: GetUserByIdRequest): UserResponse? {
        val response = apiService.getUserById(id = request.id).execute()
        return response.body()
    }

    private fun saveUserData(login: String, password: String, userResponse: UserResponse) {
        userDataStorage.saveData(
            makeToken(login = login, password = password),
            PrefDataConstants.USER_TOKEN
        )
        userDataStorage.saveData(userResponse.id, PrefDataConstants.USER_ID)
        userResponse.familyId?.let { id ->
            userDataStorage.saveData(id, PrefDataConstants.FAMILY_ID)
        }
    }

    private fun makeToken(login: String, password: String): String {
        val originalString = "${login}:${password}"
        val data = originalString.toByteArray(StandardCharsets.UTF_8)
        return "Basic ${Base64.encodeToString(data, Base64.NO_WRAP)}"
    }
}