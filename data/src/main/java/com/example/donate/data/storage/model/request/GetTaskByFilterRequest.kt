package com.example.donate.data.storage.model.request

data class GetTaskByFilterRequest(
    val executorId: String,
    val customerId: String,
    val category: Int
)
