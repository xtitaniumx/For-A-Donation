package com.example.donate.domain.usecase

import com.example.donate.domain.model.AddFamilyMemberParam
import com.example.donate.domain.model.FamilyItem
import com.example.donate.domain.repository.FamilyRepository

class AddFamilyMemberUseCase(private val familyRepository: FamilyRepository) {
    suspend operator fun invoke(addFamilyMemberParam: AddFamilyMemberParam): FamilyItem? {
        return familyRepository.addFamilyMember(param = addFamilyMemberParam)
    }
}