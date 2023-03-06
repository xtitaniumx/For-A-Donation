package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.TestAuthParam
import com.example.donate.domain.model.TestUserItem
import com.example.donate.domain.usecase.TestAuthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val testAuthUseCase: TestAuthUseCase) : ViewModel() {
    private val testUserMutable = MutableLiveData<String?>()

    val userLive: LiveData<String?> = testUserMutable

    fun authTestUser(name: String, password: String) {
        viewModelScope.launch {
            val userTest = withContext(Dispatchers.IO) {
                testAuthUseCase(
                    TestAuthParam(username = name, password = password)
                )
            }
            testUserMutable.value = userTest
        }
    }
}