package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.AddTaskRequest
import com.example.donate.data.storage.model.response.TaskResponse

interface TaskStorage {
    suspend fun getAll(): List<TaskResponse>

    suspend fun add(request: AddTaskRequest): TaskResponse?
}