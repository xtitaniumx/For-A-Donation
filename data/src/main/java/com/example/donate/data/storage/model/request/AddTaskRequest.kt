package com.example.donate.data.storage.model.request

data class AddTaskRequest(
    val name: String,
    val description: String,
    val executorId: String,
    val customerId: String,
    val points: Int,
    val categoryOfTask: Int,
    val dateTimeFinish: String
)
