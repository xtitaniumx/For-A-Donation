package com.example.donate.data.storage.model.request

data class AuthByPhoneRequest(
    val phoneNumber: String,
    val password: String,
    val remember: Boolean
)