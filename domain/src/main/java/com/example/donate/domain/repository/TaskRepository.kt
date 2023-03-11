package com.example.donate.domain.repository

import com.example.donate.domain.model.TaskItem

interface TaskRepository {
    suspend fun getAllUserTasks(): List<TaskItem>
}