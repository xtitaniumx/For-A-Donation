package com.example.donate.di

import com.example.donate.presentation.vm.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        LoginViewModel(testAuthUseCase = get(), authByPhoneUseCase = get())
    }
}