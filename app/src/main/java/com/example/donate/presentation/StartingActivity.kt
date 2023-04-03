package com.example.donate.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.presentation.util.clearStack
import com.example.donate.presentation.vm.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartingActivity : AppCompatActivity() {
    private val vm by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.autoAuthUserIfPossible()
        vm.autoAuthPossibleLive.observe(this) {
            val activityClass = if (it) {
                MainActivity::class.java
            } else {
                RegisterActivity::class.java
            }
            val intent = Intent(this, activityClass)
            intent.clearStack()
            startActivity(intent)
            finish()
        }
    }
}