package com.example.donate.data.storage.network

import android.content.Context
import android.util.Base64
import com.example.donate.data.storage.TokenStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.request.RegisterUserRequest
import com.example.donate.data.storage.model.response.RegisterUserResponse
import java.nio.charset.StandardCharsets

class NetworkUserStorage(context: Context, apiClient: ApiClient, private val tokenStorage: TokenStorage) : UserStorage {
    private val apiService = apiClient.getApiService(context)
    override suspend fun register(param: RegisterUserRequest): RegisterUserResponse {
        val response = apiService.registerUser(request = param).execute()
        if (response.isSuccessful) {
            tokenStorage.saveAuthToken(makeToken(request = param))
        }
        return response.body()!!
    }

    override fun auth(): Boolean {
        return !tokenStorage.fetchAuthToken().isNullOrEmpty()
    }

    private fun makeToken(request: RegisterUserRequest): String {
        val originalString = "${request.phoneNumber}:${request.password}"
        val data = originalString.toByteArray(StandardCharsets.UTF_8)
        return "Basic ${Base64.encodeToString(data, Base64.NO_WRAP)}"
    }
}