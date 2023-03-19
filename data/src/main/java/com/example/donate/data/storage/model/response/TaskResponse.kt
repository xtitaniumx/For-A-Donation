package com.example.donate.data.storage.model.response

data class TaskResponse(
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