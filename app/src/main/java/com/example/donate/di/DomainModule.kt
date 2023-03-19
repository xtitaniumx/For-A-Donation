package com.example.donate.di

import com.example.donate.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory {
        AddFamilyMemberUseCase(familyRepository = get())
    }

    factory {
        AddNewTaskUseCase(taskRepository = get())
    }

    factory {
        AuthByPhoneUseCase(userRepository = get())
    }

    factory {
        AuthBySavedDataUseCase(userRepository = get())
    }

    factory {
        AuthByTokenUseCase(userRepository = get())
    }

    factory {
        GetAllTasksUseCase(taskRepository = get())
    }

    factory {
        GetFamilyByIdUseCase(familyRepository = get())
    }

    factory {
        GetFamilyIdUseCase(userRepository = get())
    }

    factory {
        GetTaskByFilterUseCase(taskRepository = get())
    }

    factory {
        GetTaskByIdUseCase(taskRepository = get())
    }

    factory {
        GetUserIdUseCase(userRepository = get())
    }

    factory {
        RegisterUserUseCase(userRepository = get())
    }
}