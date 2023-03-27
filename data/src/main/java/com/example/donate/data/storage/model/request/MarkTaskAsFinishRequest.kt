package com.example.donate.data.storage.model.request

data class MarkTaskAsFinishRequest(
    val taskId: String,
    val userId: String
)