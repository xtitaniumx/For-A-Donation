package com.example.donate.presentation.vm

import androidx.lifecycle.ViewModel
import com.example.donate.domain.usecase.CreateFamilyUseCase
import com.example.donate.domain.usecase.JoinToFamilyUseCase

class RegisterViewModel(
    private val createFamilyUseCase: CreateFamilyUseCase,
    private val joinToFamilyUseCase: JoinToFamilyUseCase
) : ViewModel() {

}