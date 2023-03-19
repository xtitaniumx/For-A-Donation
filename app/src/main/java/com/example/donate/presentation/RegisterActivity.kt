package com.example.donate.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.databinding.ActivityRegisterBinding
import com.example.donate.presentation.vm.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val vm by viewModel<LoginViewModel>()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        buttonCreateFamily.setOnClickListener {
            val intent = Intent(this@RegisterActivity, CreateFamilyActivity::class.java)
            startActivity(intent)
        }

        buttonJoinToFamily.setOnClickListener {
            val intent = Intent(this@RegisterActivity, QrScannerActivity::class.java)
            startActivity(intent)
        }

        textSignIn.setOnClickListener {
            vm.authUser()
        }

        vm.tokenIsExistLive.observe(this@RegisterActivity) {
            if (it) {
                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}