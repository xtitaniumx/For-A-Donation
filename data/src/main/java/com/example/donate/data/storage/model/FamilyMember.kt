package com.example.donate.data.storage.model

import com.google.gson.annotations.SerializedName

data class FamilyMember(
    @SerializedName("id")
    val id: String,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName("last_name")
    val lastName: String,

    @SerializedName("role")
    val role: String
)