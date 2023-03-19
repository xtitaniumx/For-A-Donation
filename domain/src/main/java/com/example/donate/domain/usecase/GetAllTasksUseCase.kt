package com.example.donate.domain.usecase

import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class GetAllTasksUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(): List<TaskItem>? {
        return taskRepository.getAllTasks()
    }
}