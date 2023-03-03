package com.example.donate.data.storage.model

import com.google.gson.annotations.SerializedName

data class AuthFamilyRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)