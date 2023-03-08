package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.AuthFamilyParam
import com.example.donate.domain.model.TestAuthParam
import com.example.donate.domain.usecase.AuthByPhoneUseCase
import com.example.donate.domain.usecase.TestAuthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val testAuthUseCase: TestAuthUseCase, private val authByPhoneUseCase: AuthByPhoneUseCase) : ViewModel() {
    private val testUserMutable = MutableLiveData<String?>()

    val userLive: LiveData<String?> = testUserMutable

    fun authUser(name: String, password: String) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                authByPhoneUseCase(
                    AuthFamilyParam(phone = name, password = password)
                )
            }
            //testUserMutable.value = user
        }
    }
}