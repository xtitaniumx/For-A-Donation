package com.example.donate.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donate.R
import com.example.donate.databinding.ActivityTaskInfoBinding
import com.example.donate.presentation.util.IntentConstants
import com.example.donate.presentation.util.addChip
import com.example.donate.presentation.vm.TaskInfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskInfoActivity : AppCompatActivity() {
    private val vm by viewModel<TaskInfoViewModel>()
    private lateinit var binding: ActivityTaskInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(IntentConstants.TASK_EXECUTOR_ID)?.let { vm.getUserFromId(it) }
        binding = ActivityTaskInfoBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        textTaskName.text = intent.getStringExtra(IntentConstants.TASK_NAME)
        textTaskDesc.text = intent.getStringExtra(IntentConstants.TASK_DESCRIPTION)
        textRewardAmount.text = intent.getIntExtra(IntentConstants.TASK_POINTS, 0).toString()

        vm.userLive.observe(this@TaskInfoActivity) {
            if (it == null) return@observe

            chipGroupFamilyMembers.removeAllViews()
            val familyRoles = resources.getStringArray(R.array.family_roles)
            chipGroupFamilyMembers.addView(addChip(0, familyRoles[it.role], false))
        }
    }
}