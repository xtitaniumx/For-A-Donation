package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.GetUserByIdParam
import com.example.donate.domain.model.MarkTaskAsFinishParam
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.model.UserItem
import com.example.donate.domain.usecase.GetUserByIdUseCase
import com.example.donate.domain.usecase.MarkTaskAsFinishUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskInfoViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val markTaskAsFinishUseCase: MarkTaskAsFinishUseCase
) : ViewModel() {
    private val userMutable = MutableLiveData<UserItem?>()
    val userLive: LiveData<UserItem?> = userMutable

    private val dateTimeMutable = MutableLiveData<Pair<String, Pair<String, String>>>()
    val dateTimeLive: LiveData<Pair<String, Pair<String, String>>> = dateTimeMutable

    private val finishedTaskMutable = MutableLiveData<TaskItem?>()
    val finishedTaskLive: LiveData<TaskItem?> = finishedTaskMutable

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

    fun parseDateTime(dateTime: String) {
        val format = dateTime.split('T')
        val formatDate = format[0].replace('-', '.')
        val formatTime = format[1].split('.')[0].split(':')
        dateTimeMutable.value = Pair(formatDate, Pair(formatTime[0], formatTime[1]))
    }

    fun finishTask(taskId: String, userId: String) {
        viewModelScope.launch {
            val task = withContext(Dispatchers.IO) {
                markTaskAsFinishUseCase(MarkTaskAsFinishParam(
                    taskId = taskId,
                    userId = userId
                ))
            }
            finishedTaskMutable.value = task
        }
    }
}