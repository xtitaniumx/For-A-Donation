package com.example.donate.data.storage.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private lateinit var apiService: ApiService
    private val url = "https://c084-88-204-78-252.eu.ngrok.io"

    fun getApiService(context: Context): ApiService {
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient(context))
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }

    private fun okHttpClient(context: Context): OkHttpClient {
        // Код для debug версии приложения
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}