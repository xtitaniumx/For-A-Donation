package com.example.donate.domain.model

data class AddNewTaskParam(
    val name: String,
    val description: String,
    val executorId: String,
    val customerId: String,
    val points: Int,
    val categoryOfTask: Int,
    val dateTimeFinish: String
)