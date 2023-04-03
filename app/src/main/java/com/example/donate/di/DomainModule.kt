package com.example.donate.di

import com.example.donate.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory {
        RegisterAccountUseCase(userRepository = get())
    }

    factory {
        AuthByPhoneUseCase(userRepository = get())
    }

    factory {
        AuthByRememberDataUseCase(userRepository = get())
    }

    factory {
        LogOutAccountUseCase(userRepository = get())
    }

    factory {
        GetUserIdUseCase(userRepository = get())
    }

    factory {
        GetUserByIdUseCase(userRepository = get())
    }

    factory {
        GetFamilyIdUseCase(userRepository = get())
    }

    factory {
        GetFamilyByIdUseCase(familyRepository = get())
    }

    factory {
        AddFamilyMemberUseCase(familyRepository = get())
    }

    factory {
        AddNewTaskUseCase(taskRepository = get())
    }

    factory {
        GetAllTasksUseCase(taskRepository = get())
    }

    factory {
        GetTaskByIdUseCase(taskRepository = get())
    }

    factory {
        GetTaskByNameUseCase(taskRepository = get())
    }

    factory {
        GetTaskByFilterUseCase(taskRepository = get())
    }

    factory {
        MarkTaskAsFinishUseCase(taskRepository = get())
    }
}