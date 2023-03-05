package com.example.donate.presentation

import android.app.Activity
import androidx.core.content.ContextCompat

fun Activity.getColorCompat(id: Int): Int {
    return ContextCompat.getColor(this, id)
}