package com.example.donate.data.storage.network

import android.content.Context
import com.example.donate.data.storage.FamilyStorage
import com.example.donate.data.storage.model.request.AddFamilyMemberRequest
import com.example.donate.data.storage.model.request.GetFamilyRequest
import com.example.donate.data.storage.model.response.FamilyResponse

class NetworkFamilyStorage(context: Context, apiClient: ApiClient) : FamilyStorage {
    private val apiService = apiClient.getApiService(context)

    override suspend fun get(request: GetFamilyRequest): FamilyResponse? {
        val response = apiService.getFamilyById(familyId = request.id).execute()
        return response.body()
    }

    override suspend fun add(request: AddFamilyMemberRequest): FamilyResponse? {
        val response = apiService.addFamilyMember(
            userId = request.userId,
            familyId = request.familyId
        ).execute()
        return response.body()
    }
}