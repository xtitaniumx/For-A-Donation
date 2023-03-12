package com.example.donate.data.storage.network

import android.content.Context
import com.example.donate.data.storage.FamilyStorage
import com.example.donate.data.storage.model.request.GetFamilyRequest
import com.example.donate.data.storage.model.response.FamilyResponse

class NetworkFamilyStorage(context: Context, apiClient: ApiClient) : FamilyStorage {
    private val apiService = apiClient.getApiService(context)

    override suspend fun get(request: GetFamilyRequest): FamilyResponse? {
        val response = apiService.getFamilyById(request.id).execute()
        if (response.isSuccessful) {
            return response.body()
        }
        return null
    }
}