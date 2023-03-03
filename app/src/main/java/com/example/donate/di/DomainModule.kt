package com.example.donate.di

import com.example.donate.domain.usecase.AddFamilyMemberUseCase
import com.example.donate.domain.usecase.AuthByEmailUseCase
import com.example.donate.domain.usecase.CreateFamilyUseCase
import com.example.donate.domain.usecase.JoinToFamilyUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        AddFamilyMemberUseCase()
    }

    factory {
        AuthByEmailUseCase(userRepository = get())
    }

    factory {
        CreateFamilyUseCase()
    }

    factory {
        JoinToFamilyUseCase()
    }
}