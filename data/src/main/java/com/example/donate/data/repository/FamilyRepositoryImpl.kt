package com.example.donate.data.repository

import com.example.donate.data.storage.FamilyStorage
import com.example.donate.data.storage.model.request.AddFamilyMemberRequest
import com.example.donate.data.storage.model.request.GetFamilyRequest
import com.example.donate.data.storage.model.response.FamilyResponse
import com.example.donate.domain.model.AddFamilyMemberParam
import com.example.donate.domain.model.FamilyItem
import com.example.donate.domain.model.FamilyMemberItem
import com.example.donate.domain.model.GetFamilyParam
import com.example.donate.domain.repository.FamilyRepository

class FamilyRepositoryImpl(private val familyStorage: FamilyStorage) : FamilyRepository {
    override suspend fun getFamilyById(param: GetFamilyParam): FamilyItem? {
        val family = familyStorage.get(mapToStorage(param))
        return family?.let { mapToDomain(it) }
    }

    override suspend fun addFamilyMember(param: AddFamilyMemberParam): FamilyItem? {
        val family = familyStorage.add(mapToStorage(param))
        return family?.let { mapToDomain(it) }
    }

    private fun mapToStorage(getFamilyParam: GetFamilyParam): GetFamilyRequest {
        return GetFamilyRequest(
            id = getFamilyParam.id
        )
    }

    private fun mapToStorage(addFamilyMemberParam: AddFamilyMemberParam): AddFamilyMemberRequest {
        return AddFamilyMemberRequest(
            userId = addFamilyMemberParam.userId,
            familyId = addFamilyMemberParam.familyId
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