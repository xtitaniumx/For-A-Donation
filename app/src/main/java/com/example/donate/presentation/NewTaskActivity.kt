package com.example.donate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityNewTaskBinding
import com.google.android.material.chip.Chip


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


    }

    private fun addChip(id: Int, text: String, icon: Int): Chip {
        val chip = Chip(this)
        chip.id = id
        chip.text = text
        chip.setChipIconResource(icon)
        return chip
    }
}