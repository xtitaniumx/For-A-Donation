package com.example.donate.data.storage

import com.example.donate.data.storage.model.request.AuthFamilyRequest

interface UserStorage {
    fun auth(param: AuthFamilyRequest)
}