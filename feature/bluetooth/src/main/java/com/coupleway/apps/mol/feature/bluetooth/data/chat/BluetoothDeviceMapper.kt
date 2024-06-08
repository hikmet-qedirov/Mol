package com.coupleway.apps.mol.feature.bluetooth.data.chat

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.coupleway.apps.mol.feature.bluetooth.domain.chat.BluetoothDeviceDomain


/**
 * Created by Hikmet Qedirov on 07.04.2024.
 */

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}