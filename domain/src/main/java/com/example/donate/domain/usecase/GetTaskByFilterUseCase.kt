package com.example.donate.domain.usecase

import com.example.donate.domain.model.GetTaskByFilterParam
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class GetTaskByFilterUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(getTaskByFilterParam: GetTaskByFilterParam): List<TaskItem>? {
        return taskRepository.getTaskByFilter(param = getTaskByFilterParam)
    }
}