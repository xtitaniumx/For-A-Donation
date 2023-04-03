package com.example.donate.data.storage

interface UserDataStorage {
    fun saveData(data: String, dataId: String)

    fun fetchData(dataId: String): String?

    fun removeAllData()
}