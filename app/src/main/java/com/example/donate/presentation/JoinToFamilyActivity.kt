package com.example.donate.presentation

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.donate.R
import com.example.donate.databinding.ActivityJoinToFamilyBinding
import com.example.donate.presentation.util.IntentConstants
import com.example.donate.presentation.util.clearStack
import com.example.donate.presentation.vm.JoinToFamilyViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mindorks.editdrawabletext.DrawablePosition
import com.mindorks.editdrawabletext.onDrawableClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class JoinToFamilyActivity : AppCompatActivity() {
    private val vm by viewModel<JoinToFamilyViewModel>()
    private lateinit var binding: ActivityJoinToFamilyBinding
    private var showPasswordCreate = false
    private var showPasswordConfirm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinToFamilyBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        editTextCreatePassword.setDrawableClickListener(object: onDrawableClickListener {
            override fun onClick(target: DrawablePosition) {
                when (target) {
                    DrawablePosition.LEFT -> {
                        // change icon *** to abc
                    }
                    DrawablePosition.RIGHT -> {
                        if (showPasswordCreate) {
                            editTextCreatePassword.transformationMethod = null
                            editTextCreatePassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_password,
                                0,
                                R.drawable.ic_visibility,
                                0
                            )
                        } else {
                            editTextCreatePassword.transformationMethod = PasswordTransformationMethod()
                            editTextCreatePassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_password,
                                0,
                                R.drawable.ic_visibility_off,
                                0
                            )
                        }
                        showPasswordCreate = !showPasswordCreate
                    }
                }
            }
        })

        editTextConfirmPassword.setDrawableClickListener(object: onDrawableClickListener {
            override fun onClick(target: DrawablePosition) {
                when (target) {
                    DrawablePosition.LEFT -> {
                        // change icon *** to abc
                    }
                    DrawablePosition.RIGHT -> {
                        if (showPasswordConfirm) {
                            editTextConfirmPassword.transformationMethod = null
                            editTextConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_password,
                                0,
                                R.drawable.ic_visibility,
                                0
                            )
                        } else {
                            editTextConfirmPassword.transformationMethod = PasswordTransformationMethod()
                            editTextConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_password,
                                0,
                                R.drawable.ic_visibility_off,
                                0
                            )
                        }
                        showPasswordConfirm = !showPasswordConfirm
                    }
                }
            }
        })

        buttonRegister.setOnClickListener {
            val genderId = when (toggleButtonGender.checkedButtonId) {
                R.id.buttonGenderMale -> 0
                R.id.buttonGenderFemale -> 1
                else -> 0
            }
            val roleId = when (chipGroupSelectRole.checkedChipId) {
                R.id.chipFather -> 0
                R.id.chipMother -> 1
                R.id.chipSon -> 2
                R.id.chipDaughter -> 3
                R.id.chipGrandfather -> 4
                R.id.chipGrandmother -> 5
                else -> 0
            }
            vm.addUserToFamily(
                name = editTextYourName.text.toString(),
                phoneNumber = editTextYourPhone.text.toString(),
                password = editTextCreatePassword.text.toString(),
                passwordConfirm = editTextConfirmPassword.text.toString(),
                gender = genderId,
                role = roleId,
                familyId = intent.getStringExtra(IntentConstants.QR_CODE_VALUE)
            )
        }

        vm.errorMessageLive.observe(this@JoinToFamilyActivity) {
            MaterialAlertDialogBuilder(this@JoinToFamilyActivity)
                .setTitle("Ошибка регистрации")
                .setMessage(it.joinToString(separator = "\n"))
                .setPositiveButton("ОК") { dialog, which ->
                    dialog.cancel()
                }
                .show()
        }

        vm.userLive.observe(this@JoinToFamilyActivity) {
            val intent = Intent(this@JoinToFamilyActivity, MainActivity::class.java)
            intent.clearStack()
            startActivity(intent)
        }
    }
}