package com.example.donate.presentation

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var showPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        buttonLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
        editTextPassword.setOnLongClickListener {
            if (showPassword) {
                editTextPassword.transformationMethod = null
                editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_password,
                    0,
                    R.drawable.ic_visibility,
                    0
                )
            } else {
                editTextPassword.transformationMethod = PasswordTransformationMethod()
                editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_password,
                    0,
                    R.drawable.ic_visibility_off,
                    0
                )
            }
            showPassword = !showPassword
            return@setOnLongClickListener true
        }
        textCreateFamily.setOnClickListener { finish() }
    }
}