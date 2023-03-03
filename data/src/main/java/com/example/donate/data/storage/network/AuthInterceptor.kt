package com.example.donate.data.storage.network

import android.content.Context
import com.example.donate.data.storage.pref.SharedPrefsTokenStorage
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sharedPrefsTokenStorage = SharedPrefsTokenStorage(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sharedPrefsTokenStorage.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", it)
        }

        return chain.proceed(requestBuilder.build())
    }

}