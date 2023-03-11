package com.example.donate.di

import com.example.donate.data.repository.TaskRepositoryImpl
import com.example.donate.data.repository.UserRepositoryImpl
import com.example.donate.data.storage.TaskStorage
import com.example.donate.data.storage.TokenStorage
import com.example.donate.data.storage.UserStorage
import com.example.donate.data.storage.network.ApiClient
import com.example.donate.data.storage.network.NetworkTaskStorage
import com.example.donate.data.storage.network.NetworkUserStorage
import com.example.donate.data.storage.pref.SharedPrefsTokenStorage
import com.example.donate.domain.repository.TaskRepository
import com.example.donate.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<TokenStorage> {
        SharedPrefsTokenStorage(context = get())
    }

    single<UserStorage> {
        NetworkUserStorage(
            context = get(),
            tokenStorage = get(),
            apiClient = ApiClient()
        )
    }

    single<UserRepository> {
        UserRepositoryImpl(userStorage = get())
    }

    single<TaskStorage> {
        NetworkTaskStorage(
            context = get(),
            tokenStorage = get(),
            apiClient = ApiClient()
        )
    }

    single<TaskRepository> {
        TaskRepositoryImpl(taskStorage = get())
    }
}