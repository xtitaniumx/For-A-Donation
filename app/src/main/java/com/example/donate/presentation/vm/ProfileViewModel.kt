package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.GetUserByIdParam
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.usecase.GetUserByIdUseCase
import com.example.donate.domain.usecase.GetUserIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {
    private val userMutable = MutableLiveData<UserItem?>()
    val userLive: LiveData<UserItem?> = userMutable

    fun getUser() {
        viewModelScope.launch {
            val userId = getUserIdUseCase()
            userId?.let {
                val user = withContext(Dispatchers.IO) {
                    getUserByIdUseCase(GetUserByIdParam(id = it))
                }
                userMutable.value = user
            }
        }
    }
}