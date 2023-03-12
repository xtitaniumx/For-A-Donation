package com.example.donate.domain.usecase

import com.example.donate.domain.model.AddNewTaskParam
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class AddNewTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(addNewTaskParam: AddNewTaskParam): TaskItem? {
        return taskRepository.addNewTask(param = addNewTaskParam)
    }
}