package com.example.donate.presentation

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityLoginBinding
import com.example.donate.presentation.util.ArgumentConstants
import com.example.donate.presentation.vm.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val vm by viewModel<LoginViewModel>()
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
            vm.authUser(editTextPhone.text.toString(), editTextPassword.text.toString())
        }

        vm.errorMessageLive.observe(this@LoginActivity) {
            MaterialAlertDialogBuilder(this@LoginActivity)
                .setTitle("Ошибка входа в аккаунт")
                .setMessage(it.joinToString(separator = "\n"))
                .setPositiveButton("ОК") { dialog, which ->
                    dialog.cancel()
                }
                .show()
        }

        vm.userLive.observe(this@LoginActivity) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

        editTextPassword.setDrawableClickListener(object: onDrawableClickListener {
            override fun onClick(target: DrawablePosition) {
                when (target) {
                    DrawablePosition.LEFT -> {
                        // change icon *** to abc
                    }
                    DrawablePosition.RIGHT -> {
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
                    }
                }
            }
        })

        textCreateFamily.setOnClickListener { finish() }
    }
}