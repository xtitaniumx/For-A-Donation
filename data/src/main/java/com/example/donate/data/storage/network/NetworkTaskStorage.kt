package com.example.donate.data.storage.network

import android.content.Context
import com.example.donate.data.storage.TaskStorage
import com.example.donate.data.storage.model.request.AddTaskRequest
import com.example.donate.data.storage.model.request.GetTaskByFilterRequest
import com.example.donate.data.storage.model.request.GetTaskByIdRequest
import com.example.donate.data.storage.model.request.GetTaskByNameRequest
import com.example.donate.data.storage.model.response.TaskResponse

class NetworkTaskStorage(context: Context, apiClient: ApiClient) : TaskStorage {
    private val apiService = apiClient.getApiService(context)

    override suspend fun getAll(): List<TaskResponse>? {
        val response = apiService.getAllTasks().execute()
        if (response.isSuccessful) {
            return response.body()
        }
        return emptyList()
    }

    override suspend fun get(request: GetTaskByIdRequest): TaskResponse? {
        val response = apiService.getTaskById(id = request.taskId).execute()
        return response.body()
    }

    override suspend fun get(request: GetTaskByNameRequest): List<TaskResponse>? {
        val response = apiService.getTaskByName(name = request.name).execute()
        return response.body()
    }

    override suspend fun get(request: GetTaskByFilterRequest): List<TaskResponse>? {
        val response = apiService.getTaskByFilter(request).execute()
        if (response.isSuccessful) {
            return response.body()
        }
        return emptyList()
    }

    override suspend fun add(request: AddTaskRequest): TaskResponse? {
        val response = apiService.addTask(request).execute()
        return response.body()
    }
}