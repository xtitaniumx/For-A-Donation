package com.example.donate.domain.usecase

import com.example.donate.domain.model.MarkTaskAsFinishParam
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.repository.TaskRepository

class MarkTaskAsFinishUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(markTaskAsFinishParam: MarkTaskAsFinishParam): TaskItem? {
        return taskRepository.makeTaskAsFinish(param = markTaskAsFinishParam)
    }
}