package com.example.donate.domain.repository

import com.example.donate.domain.model.*

interface TaskRepository {
    suspend fun getAllTasks(): List<TaskItem>?

    suspend fun addNewTask(param: AddNewTaskParam): TaskItem?

    suspend fun getTaskById(param: GetTaskByIdParam): TaskItem?

    suspend fun getTaskByFilter(param: GetTaskByFilterParam): List<TaskItem>?

    suspend fun getTaskByName(param: GetTaskByNameParam): List<TaskItem>?

    suspend fun makeTaskAsFinish(param: MarkTaskAsFinishParam): TaskItem?
}