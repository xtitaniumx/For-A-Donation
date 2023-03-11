package com.example.donate.data.storage

import com.example.donate.data.storage.model.response.TaskResponse

interface TaskStorage {
    suspend fun getAll(): List<TaskResponse>
}