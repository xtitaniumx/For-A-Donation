package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.AuthByPhoneUserParam
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.usecase.AuthByPhoneUseCase
import com.example.donate.domain.usecase.AuthBySavedDataUseCase
import com.example.donate.domain.usecase.AuthByTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val authByTokenUseCase: AuthByTokenUseCase,
    private val authByPhoneUseCase: AuthByPhoneUseCase,
    private val authBySavedDataUseCase: AuthBySavedDataUseCase
) : ViewModel() {
    private val autoAuthPossibleMutable = MutableLiveData<Boolean>()
    val autoAuthPossibleLive: LiveData<Boolean> = autoAuthPossibleMutable

    private val tokenIsExistMutable = MutableLiveData<Boolean>()
    val tokenIsExistLive: LiveData<Boolean> = tokenIsExistMutable

    private val userMutable = MutableLiveData<UserItem?>()
    val userLive: LiveData<UserItem?> = userMutable

    private val errorMessageMutable = MutableLiveData<List<String>>()
    val errorMessageLive: LiveData<List<String>> = errorMessageMutable

    fun authUser() {
        tokenIsExistMutable.value = authByTokenUseCase()
    }

    fun authUser(phone: String, password: String) {
        viewModelScope.launch {
            val authResult = withContext(Dispatchers.IO) {
                authByPhoneUseCase(
                    AuthByPhoneUserParam(
                        phoneNumber = phone,
                        password = password
                    )
                )
            }
            when (authResult) {
                is UserItem -> {
                    userMutable.value = authResult
                }
                is ArrayList<*> -> {
                    errorMessageMutable.value = authResult as ArrayList<String>
                }
            }
        }
    }

    fun autoAuthUserIfPossible() {
        autoAuthPossibleMutable.value = authBySavedDataUseCase()
    }
}