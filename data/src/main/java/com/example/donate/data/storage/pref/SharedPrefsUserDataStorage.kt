package com.example.donate.data.storage.pref

import android.content.Context
import com.example.donate.data.storage.UserDataStorage

class SharedPrefsUserDataStorage(private val context: Context) : UserDataStorage {
    private val pref by lazy { context.getSharedPreferences("user_data", Context.MODE_PRIVATE) }

    override fun saveData(data: String, dataId: String) {
        pref.edit().apply {
            putString(dataId, data)
            apply()
        }
    }

    override fun fetchData(dataId: String): String? {
        return pref.getString(dataId, null)
    }

    override fun removeAllData() {
        pref.edit().clear().apply()
    }
}