package com.example.donate.data.storage.network

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.donate.data.storage.TokenStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.request.AuthFamilyRequest
import com.example.donate.data.storage.model.request.TestAuthRequest
import com.example.donate.data.storage.model.response.AuthFamilyResponse
import com.example.donate.data.storage.model.response.TestAuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets

class NetworkUserStorage(context: Context, apiClient: ApiClient, private val tokenStorage: TokenStorage) : UserStorage {
    private val apiService = apiClient.getApiService(context)

    override fun auth(param: AuthFamilyRequest) {
        apiService.auth(AuthFamilyRequest(phone = param.phone, password = param.password))
            .enqueue(object : Callback<AuthFamilyResponse> {
                override fun onResponse(call: Call<AuthFamilyResponse>, response: Response<AuthFamilyResponse>) {
                    if (response.isSuccessful) {
                        tokenStorage.saveAuthToken(getToken(request = param))
                        response.body()?.let { Log.d("info", it.message) }
                    } else {
                        // Error logging in
                    }
                }

                override fun onFailure(call: Call<AuthFamilyResponse>, t: Throwable) {
                    // Error logging in
                }
            })
    }

    private fun getToken(request: AuthFamilyRequest): String {
        val originalString = "Basic ${request.phone}:${request.password}"
        val data = originalString.toByteArray(StandardCharsets.UTF_8)
        return Base64.encodeToString(data, Base64.DEFAULT)
    }

    override suspend fun authTest(param: TestAuthRequest): TestAuthResponse? {
        val response = apiService.authTest(
            TestAuthRequest(username = param.username, password = param.password)
        ).execute()
        return response.body()
    }
}