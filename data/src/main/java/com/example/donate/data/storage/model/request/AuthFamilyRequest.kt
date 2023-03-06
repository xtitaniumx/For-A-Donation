package com.example.donate.data.storage.model.request

import com.google.gson.annotations.SerializedName

data class AuthFamilyRequest(
    @SerializedName("phone")
    val phone: String,

    @SerializedName("password")
    val password: String
)