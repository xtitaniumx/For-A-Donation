package com.example.donate.data.storage.network

import android.content.Context
import com.example.donate.data.storage.TokenStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.model.request.AuthFamilyRequest
import com.example.donate.data.storage.model.response.AuthFamilyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkUserStorage(private val context: Context, private val tokenStorage: TokenStorage, private val apiClient: ApiClient) : UserStorage {

    override fun auth(param: AuthFamilyRequest) {
        apiClient.getApiService(context)
            .auth(AuthFamilyRequest(email = param.email, password = param.password))
            .enqueue(object : Callback<AuthFamilyResponse> {
                override fun onResponse(call: Call<AuthFamilyResponse>, response: Response<AuthFamilyResponse>) {
                    val loginResponse = response.body()

                    if (loginResponse?.statusCode == 200 /*&& loginResponse.family != null*/) {
                        tokenStorage.saveAuthToken(loginResponse.authToken)
                    } else {
                        // Error logging in
                    }
                }

                override fun onFailure(call: Call<AuthFamilyResponse>, t: Throwable) {
                    // Error logging in
                }
            })
    }
}