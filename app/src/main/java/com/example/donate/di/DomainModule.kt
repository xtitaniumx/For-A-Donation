package com.example.donate.di

import com.example.donate.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory {
        AddFamilyMemberUseCase()
    }

    factory {
        AddNewTaskUseCase(taskRepository = get())
    }

    factory {
        AuthByPhoneUseCase(userRepository = get())
    }

    factory {
        AuthByTokenUseCase(userRepository = get())
    }

    factory {
        GetAllUserTasksUseCase(taskRepository = get())
    }

    factory {
        GetFamilyByIdUseCase(familyRepository = get())
    }

    factory {
        GetFamilyIdUseCase(userRepository = get())
    }

    factory {
        GetUserIdUseCase(userRepository = get())
    }

    factory {
        JoinToFamilyUseCase()
    }

    factory {
        RegisterUserUseCase(userRepository = get())
    }
}