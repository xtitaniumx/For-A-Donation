package com.example.donate.data.storage.network

import android.content.Context
import android.util.Log
import com.example.donate.data.storage.model.PrefDataConstants
import com.example.donate.data.storage.pref.SharedPrefsUserDataStorage
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context) : Interceptor {
    private val sharedPrefsUserDataStorage = SharedPrefsUserDataStorage(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        sharedPrefsUserDataStorage.setDataId(PrefDataConstants.USER_TOKEN)
        sharedPrefsUserDataStorage.fetchData()?.let {
            requestBuilder.addHeader("Authorization", it)
            Log.d("info", "add token: $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}