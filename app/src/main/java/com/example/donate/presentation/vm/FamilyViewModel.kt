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

    private val childTasksMutable = MutableLiveData<Map<Int, List<TaskItem>?>?>()
    val childTasksLive: LiveData<Map<Int, List<TaskItem>?>?> = childTasksMutable

    private val memberRoleIdMutable = MutableLiveData<Int>()
    val memberRoleIdLive: LiveData<Int> = memberRoleIdMutable

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

    fun getTaskById(id: String, roleId: Int) {
        viewModelScope.launch {
            val newTask = withContext(Dispatchers.IO) {
                getTaskByIdUseCase(GetTaskByIdParam(taskId = id))
            }

            newTask?.let { task ->
                val newMap = HashMap<Int, List<TaskItem>>()
                val newList = ArrayList<TaskItem>()
                childTasksLive.value?.get(roleId)?.let { it -> newList.addAll(it) }
                newList.add(task)
                newMap[roleId] = newList
                childTasksMutable.value = newMap
            }
        }
    }

    fun getTasksByFilter(categoryNum: Int) {
        viewModelScope.launch {
            val userId = getUserIdUseCase()
            childTasksMutable.value = withContext(Dispatchers.IO) {
                val newMap = HashMap<Int, List<TaskItem>?>()
                childFamilyMembersLive.value?.forEach { familyMember ->
                    val childTasks = getTaskByFilterUseCase(
                        GetTaskByFilterParam(
                            executorId = familyMember.id,
                            customerId = userId!!,
                            category = categoryNum
                        )
                    )
                    newMap[familyMember.role] = childTasks
                }
                newMap
            }
        }
    }

    fun setMemberRole(roleId: Int) {
        memberRoleIdMutable.value = roleId
    }
}