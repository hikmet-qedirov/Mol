package com.coupleway.apps.mol.feature.bluetooth.presentation

import com.coupleway.apps.mol.feature.bluetooth.domain.chat.BluetoothDevice

/**
 * Created by Hikmet Qedirov on 07.04.2024.
 */
data class BluetoothUiState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
    val pairedDevices: List<BluetoothDevice> = emptyList(),
)
