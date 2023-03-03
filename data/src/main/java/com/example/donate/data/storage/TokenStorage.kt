package com.example.donate.data.storage

interface TokenStorage {
    fun saveAuthToken(token: String)

    fun fetchAuthToken(): String?
}