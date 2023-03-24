package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.GetUserByIdParam
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.usecase.GetUserByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskInfoViewModel(private val getUserByIdUseCase: GetUserByIdUseCase) : ViewModel() {
    private val userMutable = MutableLiveData<UserItem?>()
    val userLive: LiveData<UserItem?> = userMutable

    fun getUserFromId(userId: String) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                getUserByIdUseCase(GetUserByIdParam(
                    id = userId
                ))
            }
            userMutable.value = user
        }
    }
}