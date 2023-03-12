package com.example.donate.domain.repository

import com.example.donate.domain.model.FamilyItem
import com.example.donate.domain.model.GetFamilyParam

interface FamilyRepository {
    suspend fun getFamilyById(param: GetFamilyParam): FamilyItem?
}