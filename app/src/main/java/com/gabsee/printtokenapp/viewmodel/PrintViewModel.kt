package com.gabsee.printtokenapp.viewmodel

import android.app.Application
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gabsee.printtokenapp.utils.BluetoothHelper
import com.gabsee.printtokenapp.utils.PrinterHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrintViewModel(application: Application) : AndroidViewModel(application) {

    private val bluetoothHelper = BluetoothHelper(application)
    private val printerHelper = PrinterHelper()

    private val _pairedDevice = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val pairedDevice: StateFlow<List<BluetoothDevice?>> = _pairedDevice

    private val _selectedDevice = MutableStateFlow<BluetoothDevice?>(null)
    val selectedDevice: StateFlow<BluetoothDevice?> = _selectedDevice

    fun loadPairedDevices() {
        _pairedDevice.value = bluetoothHelper.getPairedDevices()
    }

    fun selectDevice(device: BluetoothDevice) {
        _selectedDevice.value = device
    }

    fun printToken(token: String, onError: (String) -> Unit, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val device = _selectedDevice.value
            if (device == null) {
                onError("No printer selected")
                return@launch
            }

            try {
                printerHelper.printToken(device, token)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }


}