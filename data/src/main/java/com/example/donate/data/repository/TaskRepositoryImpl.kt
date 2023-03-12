package com.example.donate.data.repository

import com.example.donate.data.storage.TaskStorage
import com.example.donate.data.storage.model.request.AddTaskRequest
import com.example.donate.data.storage.model.response.TaskResponse
import com.example.donate.domain.model.AddNewTaskParam
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskStorage: TaskStorage) : TaskRepository {
    override suspend fun getAllUserTasks(): List<TaskItem> {
        val tasks = taskStorage.getAll()
        return mapToDomain(tasks)
    }

    override suspend fun addNewTask(param: AddNewTaskParam): TaskItem? {
        val task = taskStorage.add(mapToStorage(param))
        return task?.let { mapToDomain(it) }
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

    private fun mapToStorage(addNewTaskParam: AddNewTaskParam): AddTaskRequest {
        return AddTaskRequest(
            name = addNewTaskParam.name,
            description = addNewTaskParam.description,
            executorId = addNewTaskParam.executorId,
            customerId = addNewTaskParam.customerId,
            points = addNewTaskParam.points,
            categoryOfTask = addNewTaskParam.categoryOfTask,
            dateTimeFinish = addNewTaskParam.dateTimeFinish
        )
    }

    private fun mapToDomain(taskResponse: TaskResponse): TaskItem {
        return TaskItem(
            id = taskResponse.id,
            name = taskResponse.name,
            description = taskResponse.description,
            executorId = taskResponse.executorId,
            customerId = taskResponse.customerId,
            points = taskResponse.points,
            categoryOfTask = taskResponse.categoryOfTask,
            dateTimeFinish = taskResponse.dateTimeFinish,
            isFinished = taskResponse.isFinished
        )
    }
}