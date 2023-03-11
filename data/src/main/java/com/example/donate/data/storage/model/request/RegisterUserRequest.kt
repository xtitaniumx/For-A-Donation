package com.example.donate.data.storage.model.request

data class RegisterUserRequest(
    val name: String,
    val phoneNumber: String,
    val password: String,
    val passwordConfirm: String,
    val gender: Int,
    val role: Int,
    val familyId: String?
)