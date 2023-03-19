package com.example.donate.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.donate.databinding.ActivityQrScannerBinding
import com.example.donate.presentation.util.IntentConstants
import com.example.donate.presentation.vm.CameraXViewModel
import com.example.donate.presentation.vm.QrScannerViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors

class QrScannerActivity : AppCompatActivity() {
    private val vm by viewModel<QrScannerViewModel>()
    private lateinit var binding: ActivityQrScannerBinding

    private var previewView: PreviewView? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraSelector: CameraSelector? = null
    private var lensFacing = CameraSelector.LENS_FACING_BACK
    private var previewUseCase: Preview? = null
    private var analysisUseCase: ImageAnalysis? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScannerBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
        setupCamera()
    }

    private fun init() {
        vm.qrCodeValueLive.observe(this) {
            if (it == null) return@observe

            val intent = Intent(this, JoinToFamilyActivity::class.java).apply {
                putExtra(IntentConstants.QR_CODE_VALUE, it)
            }
            startActivity(intent)
            finish()
        }
    }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Log.d("info", "registerCameraPermission - Camera Permission Granted")
                bindCameraUseCases()
            } else {
                Log.d("info", "registerCameraPermission - Camera Permission NOT Granted")
                showPermissionAlert()
            }
        }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPermissionAlert() {
        // диалог почему необходимо разрешение
    }

    private fun setupCamera() {
        previewView = binding.previewQrScanner
        cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CameraXViewModel::class.java]
            .processCameraProvider
            .observe(this) { provider: ProcessCameraProvider? ->
                cameraProvider = provider
                if (isCameraPermissionGranted()) {
                    bindCameraUseCases()
                } else {
                    requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
    }

    private fun bindCameraUseCases() {
        bindPreviewUseCase()
        bindAnalyseUseCase()
    }

    private fun bindPreviewUseCase() {
        if (cameraProvider == null) {
            return
        }
        if (previewUseCase != null) {
            cameraProvider?.unbind(previewUseCase)
        }

        previewUseCase = previewView?.display?.rotation?.let {
            Preview.Builder()
                .setTargetRotation(it)
                .build()
        }
        previewUseCase?.setSurfaceProvider(previewView!!.surfaceProvider)

        try {
            cameraSelector?.let {
                cameraProvider?.bindToLifecycle(
                    this,
                    it,
                    previewUseCase
                )
            }
        } catch (illegalStateException: IllegalStateException) {
            Log.e("info", illegalStateException.message ?: "IllegalStateException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e("info", illegalArgumentException.message ?: "IllegalArgumentException")
        }
    }

    private fun bindAnalyseUseCase() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()

        val barcodeScanner = BarcodeScanning.getClient(options)

        if (cameraProvider == null) {
            return
        }

        if (analysisUseCase != null) {
            cameraProvider?.unbind(analysisUseCase)
        }

        analysisUseCase = previewView?.display?.rotation?.let {
            ImageAnalysis.Builder()
                .setTargetRotation(it)
                .build()
        }

        // Initialize our background executor
        val cameraExecutor = Executors.newSingleThreadExecutor()

        analysisUseCase?.setAnalyzer(
            cameraExecutor
        ) { imageProxy ->
            processImageProxy(barcodeScanner, imageProxy)
        }

        try {
            cameraSelector?.let {
                cameraProvider?.bindToLifecycle(
                    this,
                    it,
                    analysisUseCase
                )
            }
        } catch (illegalStateException: IllegalStateException) {
            Log.e("info", illegalStateException.message ?: "IllegalStateException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e("info", illegalArgumentException.message ?: "IllegalArgumentException")
        }
    }
    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    private fun processImageProxy(barcodeScanner: BarcodeScanner, imageProxy: ImageProxy) {
        val inputImage = InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)

        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                vm.getValueFromQrCode(barcodes)
            }
            .addOnFailureListener {
                Log.e("info", it.message ?: it.toString())
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}