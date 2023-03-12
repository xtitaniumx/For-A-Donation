package com.example.donate.data.storage.model.response

import com.example.donate.data.storage.model.FamilyMember

data class FamilyResponse(
    val id: String,
    val members: List<FamilyMember>
)