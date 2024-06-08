package com.coupleway.apps.mol.feature.bluetooth.presentation.devices.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coupleway.apps.mol.feature.bluetooth.R
import com.coupleway.apps.mol.feature.bluetooth.domain.chat.BluetoothDevice

/**
 * Created by Hikmet Qedirov on 07.04.2024.
 */

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    onClick: (BluetoothDevice) -> Unit,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = stringResource(R.string.paired_devices),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(pairedDevices) { device ->
            Text(
                text = device.name ?: stringResource(R.string.nameless),
                modifier = Modifier.fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(16.dp),
            )

        }

        item {
            Text(
                text = stringResource(R.string.scanned_devices),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(scannedDevices) { device ->
            Text(
                text = device.name ?: stringResource(R.string.nameless),
                modifier = Modifier.fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(16.dp),
            )

        }
    }

}