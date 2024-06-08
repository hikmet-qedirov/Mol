package com.coupleway.apps.mol.feature.bluetooth.di

import android.content.Context
import com.coupleway.apps.mol.feature.bluetooth.data.chat.AndroidBluetoothController
import com.coupleway.apps.mol.feature.bluetooth.domain.chat.BluetoothController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Hikmet Qedirov on 07.04.2024.
 */

@Module
@InstallIn(SingletonComponent::class)
class FeatureBluetoothModule {

    @Provides
    @Singleton
    fun bluetoothController(
        @ApplicationContext context: Context
    ): BluetoothController = AndroidBluetoothController(context)

}