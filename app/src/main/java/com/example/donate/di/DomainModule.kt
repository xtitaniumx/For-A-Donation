package com.example.donate.di

import com.example.donate.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory {
        AddFamilyMemberUseCase()
    }

    factory {
        AuthByEmailUseCase(userRepository = get())
    }

    factory {
        TestAuthUseCase(userRepository = get())
    }

    factory {
        CreateFamilyUseCase()
    }

    factory {
        JoinToFamilyUseCase()
    }
}