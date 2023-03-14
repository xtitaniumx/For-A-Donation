package com.example.donate.di

import com.example.donate.data.repository.FamilyRepositoryImpl
import com.example.donate.data.repository.TaskRepositoryImpl
import com.example.donate.data.repository.UserRepositoryImpl
import com.example.donate.data.storage.FamilyStorage
import com.example.donate.data.storage.TaskStorage
import com.example.donate.data.storage.UserDataStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.network.ApiClient
import com.example.donate.data.storage.network.NetworkFamilyStorage
import com.example.donate.data.storage.network.NetworkTaskStorage
import com.example.donate.data.storage.network.NetworkUserStorage
import com.example.donate.data.storage.pref.SharedPrefsUserDataStorage
import com.example.donate.domain.repository.FamilyRepository
import com.example.donate.domain.repository.TaskRepository
import com.example.donate.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserDataStorage> {
        SharedPrefsUserDataStorage(context = get())
    }

    single<UserStorage> {
        NetworkUserStorage(
            context = get(),
            userDataStorage = get(),
            apiClient = ApiClient()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(
            userStorage = get(),
            userDataStorage = get()
        )
    }

    single<TaskStorage> {
        NetworkTaskStorage(
            context = get(),
            apiClient = ApiClient()
        )
    }

    single<TaskRepository> {
        TaskRepositoryImpl(taskStorage = get())
    }

    single<FamilyStorage> {
        NetworkFamilyStorage(
            context = get(),
            apiClient = ApiClient()
        )
    }

    single<FamilyRepository> {
        FamilyRepositoryImpl(familyStorage = get())
    }
}