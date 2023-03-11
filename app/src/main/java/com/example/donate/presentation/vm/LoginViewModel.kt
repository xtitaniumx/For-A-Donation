package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.usecase.AuthByTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val authByTokenUseCase: AuthByTokenUseCase) : ViewModel() {
    private val tokenIsExistMutable = MutableLiveData<Boolean>()

    val tokenIsExistLive: LiveData<Boolean> = tokenIsExistMutable

    fun authUser() {
        tokenIsExistMutable.value = authByTokenUseCase()
    }
}