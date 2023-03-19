package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.RegisterUserParam
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JoinToFamilyViewModel(private val registerUserUseCase: RegisterUserUseCase) : ViewModel() {
    private val userMutable = MutableLiveData<UserItem?>()
    val userLive: LiveData<UserItem?> = userMutable

    private val errorMessageMutable = MutableLiveData<List<String>>()
    val errorMessageLive: LiveData<List<String>> = errorMessageMutable

    fun addUserToFamily(name: String, phoneNumber: String, password: String, passwordConfirm: String, gender: Int, role: Int, familyId: String?) {
        viewModelScope.launch {
            val registerResult = withContext(Dispatchers.IO) {
                registerUserUseCase(
                    RegisterUserParam(
                        name, phoneNumber, password, passwordConfirm, gender, role, familyId
                    )
                )
            }
            when (registerResult) {
                is UserItem -> {
                    userMutable.value = registerResult
                }
                is ArrayList<*> -> {
                    errorMessageMutable.value = registerResult as ArrayList<String>
                }
            }
        }
    }
}