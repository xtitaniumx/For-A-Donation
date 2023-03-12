package com.example.donate.data.storage.model.response

import com.example.donate.data.storage.model.UserProgress

data class UserResponse(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val gender: Int,
    val role: Int,
    val familyId: String?,
    val progress: List<UserProgress>
)