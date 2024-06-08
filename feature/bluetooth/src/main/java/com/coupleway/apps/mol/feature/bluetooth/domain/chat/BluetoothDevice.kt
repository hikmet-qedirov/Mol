package com.coupleway.apps.mol.feature.bluetooth.domain.chat

/**
 * Created by Hikmet Qedirov on 07.04.2024.
 */

typealias BluetoothDeviceDomain = BluetoothDevice

data class BluetoothDevice(
    val name: String?,
    val address: String
)