package com.example.donate.domain.usecase

import com.example.donate.domain.model.FamilyItem
import com.example.donate.domain.model.GetFamilyParam
import com.example.donate.domain.repository.FamilyRepository

class GetFamilyByIdUseCase(private val familyRepository: FamilyRepository) {
    suspend operator fun invoke(getFamilyParam: GetFamilyParam): FamilyItem? {
        return familyRepository.getFamilyById(param = getFamilyParam)
    }
}