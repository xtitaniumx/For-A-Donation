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
        AuthByRememberDataUseCase(userRepository = get())
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
        GetTaskByNameUseCase(taskRepository = get())
    }

    factory {
        GetUserByIdUseCase(userRepository = get())
    }

    factory {
        GetUserIdUseCase(userRepository = get())
    }

    factory {
        MarkTaskAsFinishUseCase(taskRepository = get())
    }

    factory {
        RegisterUserUseCase(userRepository = get())
    }
}