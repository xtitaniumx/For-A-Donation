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
        intent.getStringExtra(IntentConstants.TASK_TIME_LIMIT)?.let { vm.parseDateTime(it) }
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

        vm.dateTimeLive.observe(this@TaskInfoActivity) { dateTime ->
            textDateLimit.text = dateTime.first
            val time = dateTime.second
            viewTime.textHours.text = time.first
            viewTime.textMinutes.text = time.second
        }

        vm.finishedTaskLive.observe(this@TaskInfoActivity) {
            if (it == null) return@observe
            finish()
        }

        fabFinishTask.setOnClickListener {
            intent.getStringExtra(IntentConstants.TASK_ID)?.let {
                vm.finishTask(
                    taskId = it,
                    userId = intent.getStringExtra(IntentConstants.TASK_EXECUTOR_ID)!!
                )
            }
        }
    }
}