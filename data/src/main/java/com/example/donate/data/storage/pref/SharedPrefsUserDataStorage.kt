package com.example.donate.data.storage.pref

import android.content.Context
import android.content.SharedPreferences
import com.example.donate.data.storage.UserDataStorage

class SharedPrefsUserDataStorage(private val context: Context) : UserDataStorage {
    private var pref: SharedPreferences? = null
    private var dataId: String? = null

    override fun setDataId(id: String) {
        pref = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        dataId = id
    }

    override fun saveData(data: String) {
        pref?.edit()?.apply {
            putString(dataId, data)
            apply()
        }
    }

    override fun fetchData(): String? {
        return pref?.getString(dataId, null)
    }
}