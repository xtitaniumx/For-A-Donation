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

class TasksViewModel(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val getFamilyByIdUseCase: GetFamilyByIdUseCase,
    private val getFamilyIdUseCase: GetFamilyIdUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val addNewTaskUseCase: AddNewTaskUseCase
) : ViewModel() {
    private val tasksListMutable = MutableLiveData<List<TaskItem>?>()
    val tasksListLive: LiveData<List<TaskItem>?> = tasksListMutable

    private val familyIdMutable = MutableLiveData<String?>()
    val familyIdLive: LiveData<String?> = familyIdMutable

    private val familyMembersMutable = MutableLiveData<List<FamilyMemberItem>?>()
    val familyMembersLive: LiveData<List<FamilyMemberItem>?> = familyMembersMutable

    private val taskMutable = MutableLiveData<TaskItem?>()
    val taskLive: LiveData<TaskItem?> = taskMutable

    private val dateTimeMutable = MutableLiveData<String>()
    val dateTimeLive: LiveData<String> = dateTimeMutable

    fun getAllTasks() {
        viewModelScope.launch {
            tasksListMutable.value = withContext(Dispatchers.IO) {
                getAllTasksUseCase()
            }
        }
    }

    fun getFamilyMembers(roleId: Int) {
        viewModelScope.launch {
            familyIdMutable.value = getFamilyIdUseCase()
            familyIdLive.value?.let {
                val family = withContext(Dispatchers.IO) {
                    getFamilyByIdUseCase(GetFamilyParam(id = familyIdLive.value!!))
                }
                familyMembersMutable.value = family?.members?.filter { it.role == 2 || it.role == 3 }
            }
        }
    }

    fun addNewTask(name: String, desc: String, executorId: String, points: Int, category: Int, dateTimeFinish: String) {
        viewModelScope.launch {
            val customerId = getUserIdUseCase()
            customerId?.let {
                taskMutable.value = withContext(Dispatchers.IO) {
                    addNewTaskUseCase(AddNewTaskParam(
                        name, desc, executorId, customerId, points, category, dateTimeFinish
                    ))
                }
            }
        }
    }

    fun formatDateTime(date: String, hour: Int, minute: Int) {
        val formatDate = date.split(' ')[1].replace('/', '-')
        val formatHour = if (hour in 0..9) "0$hour" else hour
        val formatMinute = if (minute in 0..9) "0$minute" else minute
        dateTimeMutable.value = "${formatDate}T${formatHour}:${formatMinute}:00.000Z"
    }
}