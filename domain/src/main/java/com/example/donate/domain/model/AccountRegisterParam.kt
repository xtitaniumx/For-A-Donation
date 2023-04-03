package com.example.donate.domain.model

data class AccountRegisterParam(
    val name: String,
    val phoneNumber: String,
    val password: String,
    val passwordConfirm: String,
    val gender: Int,
    val role: Int,
    val familyId: String?
)