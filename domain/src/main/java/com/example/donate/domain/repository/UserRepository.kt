package com.example.donate.domain.repository

import com.example.donate.domain.model.AuthFamilyParam

interface UserRepository {
    fun authByEmail(param: AuthFamilyParam)
}