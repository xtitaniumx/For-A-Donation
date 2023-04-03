package com.example.donate.di

import com.example.donate.presentation.vm.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        CreateFamilyViewModel(accountRegisterUseCase = get())
    }

    viewModel {
        FamilyViewModel(
            getFamilyByIdUseCase = get(),
            getFamilyIdUseCase = get(),
            getUserIdUseCase = get(),
            getTaskByIdUseCase = get(),
            getTaskByFilterUseCase = get()
        )
    }

    viewModel {
        JoinToFamilyViewModel(registerUserUseCase = get())
    }

    viewModel {
        LoginViewModel(
            authByRememberDataUseCase = get(),
            authByPhoneUseCase = get()
        )
    }

    viewModel {
        ProfileViewModel(
            getUserIdUseCase = get(),
            getUserByIdUseCase = get(),
            logOutAccountUseCase = get()
        )
    }

    viewModel {
        QrScannerViewModel()
    }

    viewModel {
        SearchViewModel(getTaskByNameUseCase = get())
    }

    viewModel {
        TaskInfoViewModel(
            getUserByIdUseCase = get(),
            markTaskAsFinishUseCase = get()
        )
    }

    viewModel {
        TasksViewModel(
            getAllTasksUseCase = get(),
            getFamilyByIdUseCase = get(),
            getFamilyIdUseCase = get(),
            getUserIdUseCase = get(),
            addNewTaskUseCase = get()
        )
    }
}