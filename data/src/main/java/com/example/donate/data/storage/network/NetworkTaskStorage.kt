package com.example.donate.data.storage.network

import android.content.Context
import com.example.donate.data.storage.TaskStorage
import com.example.donate.data.storage.TokenStorage
import com.example.donate.data.storage.model.response.TaskResponse

class NetworkTaskStorage(context: Context, apiClient: ApiClient, private val tokenStorage: TokenStorage) : TaskStorage {
    private val apiService = apiClient.getApiService(context)

    override suspend fun getAll(): List<TaskResponse> {
        val response = apiService.getUserTasks().execute()
        if (response.isSuccessful) {
            return response.body()!!
        }
        return emptyList()
    }
}