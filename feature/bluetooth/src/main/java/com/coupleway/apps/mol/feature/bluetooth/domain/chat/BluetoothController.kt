package com.coupleway.apps.mol.feature.bluetooth.domain.chat

import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Hikmet Qedirov on 07.04.2024.
 */
interface BluetoothController {

    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>

    fun startDiscovery()
    fun stopDiscovery()

    fun release()
}