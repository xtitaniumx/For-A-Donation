package com.example.donate.data.repository

import com.example.donate.data.storage.FamilyStorage
import com.example.donate.data.storage.model.request.GetFamilyRequest
import com.example.donate.data.storage.model.response.FamilyResponse
import com.example.donate.domain.model.FamilyItem
import com.example.donate.domain.model.FamilyMemberItem
import com.example.donate.domain.model.GetFamilyParam
import com.example.donate.domain.repository.FamilyRepository

class FamilyRepositoryImpl(private val familyStorage: FamilyStorage) : FamilyRepository {
    override suspend fun getFamilyById(param: GetFamilyParam): FamilyItem? {
        val task = familyStorage.get(mapToStorage(param))
        return task?.let { mapToDomain(it) }
    }

    private fun mapToStorage(getFamilyParam: GetFamilyParam): GetFamilyRequest {
        return GetFamilyRequest(
            id = getFamilyParam.id
        )
    }

    private fun mapToDomain(familyResponse: FamilyResponse): FamilyItem {
        val familyMembers = ArrayList<FamilyMemberItem>()
        familyResponse.members.forEach {
            familyMembers.add(
                FamilyMemberItem(
                    id = it.id,
                    name = it.name,
                    gender = it.gender,
                    role = it.role
                )
            )
        }
        return FamilyItem(
            id = familyResponse.id,
            members = familyMembers
        )
    }
}