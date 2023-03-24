package com.example.donate.data.repository

import com.example.donate.data.storage.TaskStorage
import com.example.donate.data.storage.model.request.AddTaskRequest
import com.example.donate.data.storage.model.request.GetTaskByFilterRequest
import com.example.donate.data.storage.model.request.GetTaskByIdRequest
import com.example.donate.data.storage.model.request.GetTaskByNameRequest
import com.example.donate.data.storage.model.response.TaskResponse
import com.example.donate.domain.model.*
import com.example.donate.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskStorage: TaskStorage) : TaskRepository {
    override suspend fun getAllTasks(): List<TaskItem>? {
        val tasks = taskStorage.getAll()
        return tasks?.let { mapToDomain(it) }
    }

    override suspend fun addNewTask(param: AddNewTaskParam): TaskItem? {
        val task = taskStorage.add(mapToStorage(param))
        return task?.let { mapToDomain(it) }
    }

    override suspend fun getTaskById(param: GetTaskByIdParam): TaskItem? {
        val task = taskStorage.get(mapToStorage(param))
        return task?.let { mapToDomain(it) }
    }

    override suspend fun getTaskByFilter(param: GetTaskByFilterParam): List<TaskItem>? {
        val task = taskStorage.get(mapToStorage(param))
        return task?.let { mapToDomain(it) }
    }

    override suspend fun getTaskByName(param: GetTaskByNameParam): List<TaskItem>? {
        val task = taskStorage.get(mapToStorage(param))
        return task?.let { mapToDomain(it) }
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
            isFinished = taskResponse.isFinished,
            isPerformed = taskResponse.isPerformed
        )
    }

    private fun mapToDomain(list: List<TaskResponse>): List<TaskItem> {
        val tasksList = ArrayList<TaskItem>()
        list.forEach {
            tasksList.add(mapToDomain(it))
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

    private fun mapToStorage(getTaskByIdParam: GetTaskByIdParam): GetTaskByIdRequest {
        return GetTaskByIdRequest(
            taskId = getTaskByIdParam.taskId
        )
    }

    private fun mapToStorage(getTaskByFilterParam: GetTaskByFilterParam): GetTaskByFilterRequest {
        return GetTaskByFilterRequest(
            executorId = getTaskByFilterParam.executorId,
            customerId = getTaskByFilterParam.customerId,
            category = getTaskByFilterParam.category
        )
    }

    private fun mapToStorage(getTaskByNameParam: GetTaskByNameParam): GetTaskByNameRequest {
        return GetTaskByNameRequest(
            name = getTaskByNameParam.name
        )
    }
}