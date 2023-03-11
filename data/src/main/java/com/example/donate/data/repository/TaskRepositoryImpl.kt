package com.example.donate.data.repository

import com.example.donate.data.storage.TaskStorage
import com.example.donate.data.storage.model.response.TaskResponse
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskStorage: TaskStorage) : TaskRepository {
    override suspend fun getAllUserTasks(): List<TaskItem> {
        val tasks = taskStorage.getAll()
        return mapToDomain(tasks)
    }

    private fun mapToDomain(list: List<TaskResponse>): List<TaskItem> {
        val tasksList = ArrayList<TaskItem>()
        list.forEach {
            tasksList.add(
                TaskItem(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    executorId = it.executorId,
                    customerId = it.customerId,
                    points = it.points,
                    categoryOfTask = it.categoryOfTask,
                    dateTimeFinish = it.dateTimeFinish,
                    isFinished = it.isFinished
                )
            )
        }
        return tasksList
    }
}