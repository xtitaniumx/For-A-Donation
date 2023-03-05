package com.example.donate.data.storage.model

import com.example.donate.data.storage.model.enums.Gender
import com.example.donate.data.storage.model.enums.Role
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("Name")
    val name: String,

    @SerializedName("PhoneNumber")
    val phoneNumber: String,

    @SerializedName("Gender")
    val gender: Gender,

    @SerializedName("Role")
    val role: Role,

    @SerializedName("Progress")
    val progress: List<UserProgress>,

    @SerializedName("FamilyId")
    val familyId: Int
)