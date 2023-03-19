package com.example.donate.domain.usecase

import com.example.donate.domain.model.GetTaskByIdParam
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class GetTaskByIdUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(getTaskByIdParam: GetTaskByIdParam): TaskItem? {
        return taskRepository.getTaskById(param = getTaskByIdParam)
    }
}