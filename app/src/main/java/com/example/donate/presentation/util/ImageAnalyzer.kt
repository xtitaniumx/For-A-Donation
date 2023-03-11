package com.example.donate.presentation.util

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class ImageAnalyzer(private val listener: OnGetQrCodesListener) : ImageAnalysis.Analyzer {
    interface OnGetQrCodesListener {
        fun getQrCodes(qrCodes: List<Barcode>)
    }

    override fun analyze(images: ImageProxy) {
        scanBarCode(images)
    }

    @androidx.annotation.OptIn(ExperimentalGetImage::class)
    private fun scanBarCode(images: ImageProxy) {
        images.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(image, images.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient()
            scanner.process(inputImage)
                .addOnCompleteListener {
                    images.close()
                    if (it.isSuccessful) {
                        readBarCode(it.result as List<Barcode>)
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
        }
    }

    private fun readBarCode(barcodes: List<Barcode>) {
        listener.getQrCodes(qrCodes = barcodes)
    }
}