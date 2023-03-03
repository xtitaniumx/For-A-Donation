package com.example.donate.data.storage

import com.example.donate.data.storage.model.*

interface UserStorage {
    fun auth(param: AuthFamilyRequest)
}