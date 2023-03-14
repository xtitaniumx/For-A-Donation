package com.example.donate.di

import com.example.donate.presentation.vm.CreateFamilyViewModel
import com.example.donate.presentation.vm.LoginViewModel
import com.example.donate.presentation.vm.QrScannerViewModel
import com.example.donate.presentation.vm.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        CreateFamilyViewModel(registerUserUseCase = get())
    }

    viewModel {
        LoginViewModel(
            authByTokenUseCase = get(),
            authByPhoneUseCase = get()
        )
    }

    viewModel {
        QrScannerViewModel()
    }

    viewModel {
        TasksViewModel(
            getAllUserTasksUseCase = get(),
            getFamilyByIdUseCase = get(),
            getFamilyIdUseCase = get(),
            getUserIdUseCase = get(),
            addNewTaskUseCase = get()
        )
    }
}