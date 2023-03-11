package com.example.donate.presentation

import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.donate.databinding.ActivityQrScannerBinding
import com.example.donate.presentation.vm.QrScannerViewModel
import com.example.donate.presentation.util.ImageAnalyzer
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QrScannerActivity : AppCompatActivity(), ImageAnalyzer.OnGetQrCodesListener {
    private val vm by viewModel<QrScannerViewModel>()
    private lateinit var binding: ActivityQrScannerBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var analyzer: ImageAnalyzer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScannerBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() {
        analyzer = ImageAnalyzer(this)
        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))

        vm.qrCodeValueLive.observe(this) {
            it?.let { rawValue ->
                Log.d("info", rawValue)
                finish()
            }
        }
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider?) {
        val preview = Preview.Builder().build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        preview.setSurfaceProvider(binding.previewQrScanner.surfaceProvider)

        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280, 720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(cameraExecutor, analyzer)
        cameraProvider?.bindToLifecycle(this as LifecycleOwner,
            cameraSelector, imageAnalysis, preview)
    }

    override fun getQrCodes(qrCodes: List<Barcode>) {
        vm.getValueFromQrCode(list = qrCodes)
    }
}