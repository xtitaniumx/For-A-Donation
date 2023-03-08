package com.example.donate.data.storage.model

import com.google.gson.annotations.SerializedName

data class Family(
    @SerializedName("id")
    val id: Int,

    @SerializedName("members")
    val members: List<Int>
)