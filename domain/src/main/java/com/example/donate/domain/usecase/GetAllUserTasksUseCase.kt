package com.example.donate.domain.usecase

import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class GetAllUserTasksUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(): List<TaskItem> {
        return taskRepository.getAllUserTasks()
    }
}