package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.GetTaskByNameParam
import com.example.donate.domain.model.TaskItem
import com.example.donate.domain.usecase.GetTaskByNameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val getTaskByNameUseCase: GetTaskByNameUseCase) : ViewModel() {
    private val tasksListMutable = MutableLiveData<List<TaskItem>?>()
    val tasksListLive: LiveData<List<TaskItem>?> = tasksListMutable

    fun searchTasksByName(query: String) {
        viewModelScope.launch {
            tasksListMutable.value = withContext(Dispatchers.IO) {
                getTaskByNameUseCase(GetTaskByNameParam(
                    name = query
                ))
            }
        }
    }
}