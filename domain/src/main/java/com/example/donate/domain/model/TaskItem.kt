package com.example.donate.domain.model

data class TaskItem(
    val id: String,
    val name: String,
    val description: String,
    val executorId: String,
    val customerId: String,
    val points: Int,
    val categoryOfTask: Int,
    val dateTimeFinish: String,
    val isFinished: Boolean,
    val isPerformed: Boolean
)