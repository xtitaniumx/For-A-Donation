package com.example.donate.domain.model

data class GetTaskByFilterParam(
    val executorId: String,
    val customerId: String,
    val category: Int
)