package com.example.donate.domain.repository

import com.example.donate.domain.model.AddNewTaskParam
import com.example.donate.domain.model.TaskItem

interface TaskRepository {
    suspend fun getAllUserTasks(): List<TaskItem>

    suspend fun addNewTask(param: AddNewTaskParam): TaskItem?
}