package com.example.donate.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityNewTaskBinding
import com.example.donate.presentation.util.ArgumentConstants
import com.example.donate.presentation.vm.TasksViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class NewTaskActivity : AppCompatActivity() {
    private val vm by viewModel<TasksViewModel>()
    private lateinit var binding: ActivityNewTaskBinding
    private val executorList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(ArgumentConstants.FAMILY_ID)?.let { vm.getFamilyById(it) }
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        vm.familyLive.observe(this@NewTaskActivity) { family ->
            var chipCount = 0
            family?.members?.forEach {
                executorList.add(it.id)
                chipGroupFamilyMembers.addView(addChip(chipCount, it.name, R.drawable.ic_profile))
                chipCount++
            }
        }

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
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
                executorId = executorList[chipGroupFamilyMembers.checkedChipId],
                customerId = intent.getStringExtra(ArgumentConstants.USER_ID)!!,
                points = sliderReward.value.toInt(),
                category = 0,
                dateTimeFinish = vm.dateTimeLive.value!!
            )
        }
    }

    // перенести во viewModel


    private fun addChip(id: Int, text: String, icon: Int): Chip {
        val chip = Chip(this)
        chip.id = id
        chip.text = text
        chip.setChipIconResource(icon)
        return chip
    }
}