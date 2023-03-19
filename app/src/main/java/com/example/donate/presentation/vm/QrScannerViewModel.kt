package com.example.donate.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QrScannerViewModel : ViewModel() {
    private val qrCodeValueMutable = MutableLiveData<String?>()

    val qrCodeValueLive: LiveData<String?> = qrCodeValueMutable

    fun getValueFromQrCode(list: List<Barcode>) {
        viewModelScope.launch {
            val rawValue = withContext(Dispatchers.IO) {
                if (list.isNotEmpty()) {
                    list[0].rawValue
                } else {
                    null
                }
            }
            qrCodeValueMutable.value = rawValue
        }
    }
}