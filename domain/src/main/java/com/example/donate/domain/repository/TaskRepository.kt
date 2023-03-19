package com.example.donate.domain.repository

import com.example.donate.domain.model.AddNewTaskParam
import com.example.donate.domain.model.GetTaskByFilterParam
import com.example.donate.domain.model.GetTaskByIdParam
import com.example.donate.domain.model.TaskItem

interface TaskRepository {
    suspend fun getAllTasks(): List<TaskItem>?

    suspend fun addNewTask(param: AddNewTaskParam): TaskItem?

    suspend fun getTaskById(param: GetTaskByIdParam): TaskItem?

    suspend fun getTaskByFilter(param: GetTaskByFilterParam): List<TaskItem>?
}