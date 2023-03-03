package com.example.donate.data.storage.model

import com.google.gson.annotations.SerializedName

data class FamilyNet(
    @SerializedName("members_id")
    val members: List<Int>
)