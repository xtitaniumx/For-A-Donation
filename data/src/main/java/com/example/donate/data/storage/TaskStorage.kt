package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.*
import com.example.donate.data.storage.model.response.TaskResponse

interface TaskStorage {
    suspend fun getAll(): List<TaskResponse>?

    suspend fun get(request: GetTaskByIdRequest): TaskResponse?

    suspend fun get(request: GetTaskByNameRequest): List<TaskResponse>?

    suspend fun get(request: GetTaskByFilterRequest): List<TaskResponse>?

    suspend fun add(request: AddTaskRequest): TaskResponse?

    suspend fun markFinish(request: MarkTaskAsFinishRequest): TaskResponse?
}