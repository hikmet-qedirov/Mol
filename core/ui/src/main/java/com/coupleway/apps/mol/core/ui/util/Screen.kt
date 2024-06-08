package com.coupleway.apps.mol.core.ui.util

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
sealed class Screen(val route: String) {
    data object NotesScreen : Screen("notes_screen")
    data object AddEditNoteScreen : Screen("add_edit_note_screen")
    data object ConnectedBluetoothDevicesScreen : Screen("connected_bluetooth_devices_screen")
}