package com.example.donate.presentation.util

import android.app.Activity
import androidx.core.content.ContextCompat
import com.example.donate.databinding.DialogLoadingDataBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Activity.getColorCompat(id: Int): Int {
    return ContextCompat.getColor(this, id)
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