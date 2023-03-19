package com.example.donate.presentation

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.donate.databinding.ActivityAddFamilyMemberBinding
import com.example.donate.presentation.util.IntentConstants
import io.github.g0dkar.qrcode.QRCode

class AddFamilyMemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFamilyMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFamilyMemberBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        intent.getStringExtra(IntentConstants.QR_CODE_VALUE)?.let {
            val qrCodeBitmap = QRCode(it).render().nativeImage() as Bitmap
            imageQrCode.setImageBitmap(qrCodeBitmap)
        }
    }
}