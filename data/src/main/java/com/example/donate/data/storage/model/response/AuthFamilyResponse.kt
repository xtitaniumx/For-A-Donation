package com.example.donate.data.storage.model.response

import com.google.gson.annotations.SerializedName

data class AuthFamilyResponse(
    @SerializedName("message")
    val message: String
)