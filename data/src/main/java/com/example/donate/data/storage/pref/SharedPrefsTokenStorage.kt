package com.example.donate.data.storage.pref

import android.content.Context
import android.content.SharedPreferences
import com.example.donate.data.storage.TokenStorage

class SharedPrefsTokenStorage(context: Context) : TokenStorage {
    private var prefs: SharedPreferences = context.getSharedPreferences(
        TOKEN_PREF, Context.MODE_PRIVATE
    )

    companion object {
        const val TOKEN_PREF = "user_token"
    }

    override fun saveAuthToken(token: String) {
        prefs.edit().apply {
            putString(TOKEN_PREF, token)
            apply()
        }
    }

    override fun fetchAuthToken(): String? {
        return prefs.getString(TOKEN_PREF, null)
    }
}