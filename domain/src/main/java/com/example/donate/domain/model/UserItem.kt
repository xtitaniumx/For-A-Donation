package com.example.donate.domain.model

data class UserItem(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val gender: Int,
    val role: Int,
    val familyId: String?,
    val progress: List<UserProgressItem>
)