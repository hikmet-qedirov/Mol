package com.coupleway.apps.mol.feature.bluetooth.presentation.devices

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coupleway.apps.mol.feature.bluetooth.presentation.BluetoothUiState
import com.coupleway.apps.mol.feature.bluetooth.presentation.devices.component.BluetoothDeviceList

/**
 * Created by Hikmet Qedirov on 07.04.2024.
 */

@Composable
fun BluetoothDevicesScreen(
    state: BluetoothUiState,
    onStartScan: () -> Unit,
    onStopScan: () -> Unit
) {
    BluetoothDeviceList(
        modifier = Modifier,
        pairedDevices = state.pairedDevices,
        scannedDevices = state.scannedDevices,
        onClick = {

        }
    )

}