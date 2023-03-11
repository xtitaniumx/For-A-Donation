package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.usecase.GetAllUserTasksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksViewModel(private val getAllUserTasksUseCase: GetAllUserTasksUseCase) : ViewModel() {
    private val tasksListMutable = MutableLiveData<List<TaskItem>?>()
    val tasksListLive: LiveData<List<TaskItem>?> = tasksListMutable

    fun getAllTasks() {
        viewModelScope.launch {
            tasksListMutable.value = withContext(Dispatchers.IO) {
                getAllUserTasksUseCase()
            }
        }
    }
}