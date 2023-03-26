package com.example.donate.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityNewTaskBinding
import com.example.donate.presentation.util.IntentConstants
import com.example.donate.presentation.util.addChip
import com.example.donate.presentation.vm.TasksViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NewTaskActivity : AppCompatActivity() {
    private val vm by viewModel<TasksViewModel>()
    private lateinit var binding: ActivityNewTaskBinding
    private val familyRoles by lazy { resources.getStringArray(R.array.family_roles) }
    private val executorMap = HashMap<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.getFamilyMembers(intent.getIntExtra(IntentConstants.MEMBER_ROLE, -1))
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        /*vm.familyMembersLive.observe(this@NewTaskActivity) { familyMembers ->
            executorMap.clear()
            chipGroupFamilyMembers.removeAllViews()

            familyMembers?.forEach {
                executorMap[it.role] = it.id
                chipGroupFamilyMembers.addView(addChip(it.role, familyRoles[it.role]))
            }

            familyMembers?.let { chipGroupFamilyMembers.check(it[0].role) }
        }*/
        vm.familyMemberLive.observe(this@NewTaskActivity) { familyMember ->
            if (familyMember == null) return@observe
            chipGroupFamilyMembers.removeAllViews()
            chipGroupFamilyMembers.addView(addChip(familyMember.role, familyRoles[familyMember.role], false))
        }

        val taskCategories = resources.getStringArray(R.array.task_categories)
        taskCategories.forEach {
            chipGroupFilters.addView(addChip(taskCategories.indexOf(it), it))
        }
        chipGroupFilters.check(0)

        val weekDays = resources.getStringArray(R.array.weekdays)
        weekDays.forEach {
            chipGroupWeekDay.addView(addChip(weekDays.indexOf(it), it))
        }
        chipGroupWeekDay.check(0)

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
            editTextDate.setText("Дата: " + formatter.format(Date(it)))
        }

        editTextDate.keyListener = null
        editTextDate.setOnClickListener {
            if (!datePicker.isVisible) {
                datePicker.show(supportFragmentManager, "tag")
            }
        }

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setInputMode(INPUT_MODE_CLOCK)
            .setTitleText("Select Appointment time")
            .build()

        timePicker.addOnPositiveButtonClickListener {
            if (timePicker.hour != 0 && timePicker.minute != 0) {
                editTextTime.setText("Время: ${timePicker.hour} ч. ${timePicker.minute} мин.")
            } else if (timePicker.hour != 0) {
                editTextTime.setText("Время: ${timePicker.hour} ч.")
            } else if (timePicker.minute != 0) {
                editTextTime.setText("Время: ${timePicker.minute} мин.")
            }
        }

        editTextTime.keyListener = null
        editTextTime.setOnClickListener {
            if (!timePicker.isVisible) {
                timePicker.show(supportFragmentManager, "tag")
            }
        }

        buttonNewTask.setOnClickListener {
            if (editTextDate.text.toString() == "" || editTextTime.text.toString() == "") {
                return@setOnClickListener
            }
            vm.formatDateTime(editTextDate.text.toString(), timePicker.hour, timePicker.minute)
            vm.addNewTask(
                name = editTextTaskName.text.toString(),
                desc = editTextTaskDesc.text.toString(),
                executorId = vm.familyMemberLive.value!!.id,
                points = sliderReward.value.toInt(),
                category = chipGroupFilters.checkedChipId,
                dateTimeFinish = vm.dateTimeLive.value!!
            )
        }

        vm.taskLive.observe(this@NewTaskActivity) {
            val data = Intent().apply {
                putExtra(IntentConstants.NEW_TASK_ID, it?.id)
            }
            setResult(RESULT_OK, data)
            finish()
        }
    }
}