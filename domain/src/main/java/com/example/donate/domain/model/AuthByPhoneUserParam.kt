package com.example.donate.domain.model

data class AuthByPhoneUserParam(
    val phoneNumber: String,
    val password: String,
    val remember: Boolean
)