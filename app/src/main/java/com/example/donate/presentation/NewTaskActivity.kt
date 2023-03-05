package com.example.donate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityNewTaskBinding
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class NewTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        chipGroupFamilyMembers.addView(addChip(1, "Chip 1", R.drawable.ic_profile))

        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val formatter = SimpleDateFormat("Дата: dd/MM/yyyy", Locale.getDefault())
            editTextDate.setText(formatter.format(Date(it)))
        }

        editTextDate.keyListener = null
        editTextDate.setOnClickListener {
            datePicker.show(supportFragmentManager, "tag")
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
            timePicker.show(supportFragmentManager, "tag")
        }
    }

    private fun addChip(id: Int, text: String, icon: Int): Chip {
        val chip = Chip(this)
        chip.id = id
        chip.text = text
        chip.setChipIconResource(icon)
        return chip
    }
}