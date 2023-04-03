package com.example.donate.presentation.util

import android.app.Activity
import android.content.Intent
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.donate.R
import com.example.donate.databinding.DialogLoadingDataBinding
import com.example.donate.domain.model.TaskItem
import com.example.donate.presentation.TaskInfoActivity
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Activity.getColorCompat(id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Intent.clearStack() {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
}

fun Activity.createLoadingDialog(title: String): MaterialAlertDialogBuilder {
    val dialogBuilder = MaterialAlertDialogBuilder(this).apply {
        DialogLoadingDataBinding.inflate(layoutInflater).apply {
            textLoadingInfo.text = title
            setView(this.root)
        }
    }
    return dialogBuilder
}

fun Activity.addChip(chipId: Int, chipText: String, checkable: Boolean = true): Chip {
    val chip = Chip(this).apply {
        id = chipId
        text = chipText
        checkedIcon = AppCompatResources.getDrawable(this@addChip, R.drawable.ic_checked)
        isCheckedIconVisible = checkable
        isCheckable = checkable
    }
    return chip
}

fun Activity.showTaskInfo(taskItem: TaskItem) {
    val intent = Intent(this, TaskInfoActivity::class.java).apply {
        putExtra(IntentConstants.TASK_ID, taskItem.id)
        putExtra(IntentConstants.TASK_NAME, taskItem.name)
        putExtra(IntentConstants.TASK_DESCRIPTION, taskItem.description)
        putExtra(IntentConstants.TASK_EXECUTOR_ID, taskItem.executorId)
        putExtra(IntentConstants.TASK_POINTS, taskItem.points)
        putExtra(IntentConstants.TASK_TIME_LIMIT, taskItem.dateTimeFinish)
        putExtra(IntentConstants.TASK_IS_FINISHED, taskItem.isFinished)
    }
    startActivity(intent)
}