package com.example.donate.domain.usecase

import com.example.donate.domain.model.GetTaskByNameParam
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class GetTaskByNameUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(getTaskByNameParam: GetTaskByNameParam): List<TaskItem>? {
        return taskRepository.getTaskByName(param = getTaskByNameParam)
    }
}