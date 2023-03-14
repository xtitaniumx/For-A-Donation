package com.example.donate.data.storage.network

import android.content.Context
import android.util.Base64
import com.example.donate.data.storage.UserDataStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.PrefDataConstants
import com.example.donate.data.storage.model.request.AuthByPhoneRequest
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.UserResponse
import java.nio.charset.StandardCharsets

class NetworkUserStorage(context: Context, apiClient: ApiClient, private val userDataStorage: UserDataStorage) : UserStorage {
    private val apiService = apiClient.getApiService(context)
    override suspend fun register(request: RegisterUserRequest): UserResponse? {
        val response = apiService.registerUser(request).execute()
        if (response.isSuccessful) {
            userDataStorage.setDataId(PrefDataConstants.USER_TOKEN)
            userDataStorage.saveData(
                makeToken(login = request.phoneNumber, password = request.password)
            )
            response.body()?.let {
                userDataStorage.setDataId(PrefDataConstants.USER_ID)
                userDataStorage.saveData(it.id)
            }
        }
        return response.body()
    }

    override fun auth(): Boolean {
        userDataStorage.setDataId(PrefDataConstants.USER_TOKEN)
        return !userDataStorage.fetchData().isNullOrEmpty()
    }

    override suspend fun auth(request: AuthByPhoneRequest): UserResponse? {
        val response = apiService.authUserByPhone(request).execute()
        if (response.isSuccessful) {
            userDataStorage.setDataId(PrefDataConstants.USER_TOKEN)
            userDataStorage.saveData(
                makeToken(login = request.phoneNumber, password = request.password)
            )
            response.body()?.let {
                userDataStorage.setDataId(PrefDataConstants.USER_LOGGED_IN)
                userDataStorage.saveData(true.toString())
                userDataStorage.setDataId(PrefDataConstants.USER_ID)
                userDataStorage.saveData(it.id)
                userDataStorage.setDataId(PrefDataConstants.FAMILY_ID)
                it.familyId?.let { id -> userDataStorage.saveData(id) }
            }
        }
        return response.body()
    }

    private fun makeToken(login: String, password: String): String {
        val originalString = "${login}:${password}"
        val data = originalString.toByteArray(StandardCharsets.UTF_8)
        return "Basic ${Base64.encodeToString(data, Base64.NO_WRAP)}"
    }
}