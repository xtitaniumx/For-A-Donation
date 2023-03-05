package com.example.donate.data.storage.model.response

import com.google.gson.annotations.SerializedName

data class AuthFamilyResponse(
    @SerializedName("status_code")
    val statusCode: Int,

    @SerializedName("auth_token")
    val authToken: String
)