package com.example.donate.data.storage

interface UserDataStorage {
    fun setDataId(id: String)

    fun saveData(data: String)

    fun fetchData(): String?
}