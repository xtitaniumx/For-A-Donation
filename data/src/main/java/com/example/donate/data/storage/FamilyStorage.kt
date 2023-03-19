package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.AddFamilyMemberRequest
import com.example.donate.data.storage.model.request.GetFamilyRequest
import com.example.donate.data.storage.model.response.FamilyResponse

interface FamilyStorage {
    suspend fun get(request: GetFamilyRequest): FamilyResponse?

    suspend fun add(request: AddFamilyMemberRequest): FamilyResponse?
}