package com.example.donate.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donate.domain.model.*
import com.example.donate.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FamilyViewModel(
    private val getFamilyByIdUseCase: GetFamilyByIdUseCase,
    private val getFamilyIdUseCase: GetFamilyIdUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val getTaskByFilterUseCase: GetTaskByFilterUseCase
) : ViewModel() {

    private val familyIdMutable = MutableLiveData<String?>()
    val familyIdLive: LiveData<String?> = familyIdMutable

    private val childFamilyMembersMutable = MutableLiveData<List<FamilyMemberItem>?>()
    val childFamilyMembersLive: LiveData<List<FamilyMemberItem>?> = childFamilyMembersMutable

    private val childTasksMutable = MutableLiveData<Map<String, List<TaskItem>?>?>()
    val childTasksLive: LiveData<Map<String, List<TaskItem>?>?> = childTasksMutable

    private val memberIdMutable = MutableLiveData<String>()
    val memberIdLive: LiveData<String> = memberIdMutable

    fun getFamilyMembers() {
        viewModelScope.launch {
            familyIdMutable.value = getFamilyIdUseCase()
            familyIdLive.value?.let {
                val family = withContext(Dispatchers.IO) {
                    getFamilyByIdUseCase(GetFamilyParam(id = familyIdLive.value!!))
                }
                childFamilyMembersMutable.value = family?.members?.filter { it.role == 2 || it.role == 3 }
            }
        }
    }

    fun getTaskById(id: String, familyMemberId: String) {
        viewModelScope.launch {
            val newTask = withContext(Dispatchers.IO) {
                getTaskByIdUseCase(GetTaskByIdParam(taskId = id))
            }
            newTask?.let { task ->
                val newMap = HashMap<String, List<TaskItem>>()
                val newList = ArrayList<TaskItem>()

                childTasksLive.value?.get(familyMemberId)?.let { it -> newList.addAll(it) }
                newList.add(task)
                newMap[familyMemberId] = newList
                childTasksMutable.value = newMap
            }
        }
    }

    fun getTasksByFilter(categoryNum: Int) {
        viewModelScope.launch {
            val userId = getUserIdUseCase()
            userId?.let {
                val tasks = withContext(Dispatchers.IO) {
                    val newMap = HashMap<String, List<TaskItem>?>()
                    childFamilyMembersLive.value?.forEach { familyMember ->
                        val childTasks = getTaskByFilterUseCase(
                            GetTaskByFilterParam(
                                executorId = familyMember.id,
                                customerId = it,
                                category = categoryNum
                            )
                        )
                        newMap[familyMember.id] = childTasks
                    }
                    newMap
                }
                childTasksMutable.value = tasks
            }
        }
    }

    fun getTasksByFilterForMember(categoryNum: Int, familyMemberId: String) {
        viewModelScope.launch {
            val newMap = HashMap<String, List<TaskItem>?>().apply {
                putAll(childTasksLive.value!!)
            }
            Log.d("info", "try to get")
            val userId = getUserIdUseCase()
            userId?.let {
                val childTasks = withContext(Dispatchers.IO) {
                    getTaskByFilterUseCase(
                        GetTaskByFilterParam(
                            executorId = familyMemberId,
                            customerId = it,
                            category = categoryNum
                        )
                    )
                }
                newMap[familyMemberId] = childTasks
                Log.d("info", "Map: ${newMap[familyMemberId]}")
                childTasksMutable.value = newMap
            }
        }
    }

    fun setMemberId(id: String) {
        memberIdMutable.value = id
    }
}