package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.*
import com.example.donate.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val getTaskByNameUseCase: GetTaskByNameUseCase) : ViewModel() {

    private val tasksListMutable = MutableLiveData<List<TaskItem>?>()
    val tasksListLive: LiveData<List<TaskItem>?> = tasksListMutable

    fun searchTasks(query: String, category: Int) {
        viewModelScope.launch {
            val tasks = withContext(Dispatchers.IO) {
                getTaskByNameUseCase(GetTaskByNameParam(
                    name = query
                ))
            }
            val filteredList = ArrayList<TaskItem>()
            tasks?.forEach {
                if (it.categoryOfTask == category)
                    filteredList.add(it)
            }
            tasksListMutable.value = filteredList
        }
    }
}