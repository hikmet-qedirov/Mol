package com.coupleway.apps.mol.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.coupleway.apps.mol.core.ui.theme.MoLTheme
import com.coupleway.apps.mol.core.ui.util.Screen
import com.coupleway.apps.mol.feature.bluetooth.presentation.BluetoothViewModel
import com.coupleway.apps.mol.feature.bluetooth.presentation.devices.BluetoothDevicesScreen
import com.coupleway.apps.mol.feature.note.presentation.add_edit_note.AddEditNoteScreen
import com.coupleway.apps.mol.feature.note.presentation.add_edit_note.AddEditNoteViewModel
import com.coupleway.apps.mol.feature.note.presentation.notes.NotesScreen
import com.coupleway.apps.mol.feature.note.presentation.notes.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoLTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        composable(route = Screen.NotesScreen.route) {
                            val viewModel = hiltViewModel<NotesViewModel>()
                            val state = viewModel.state.value
                            NotesScreen(
                                state = state,
                                onEvent = viewModel::onEvent
                            ) {
                                navController.navigate(it)
                            }
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val viewModel = hiltViewModel<AddEditNoteViewModel>()
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                eventFlow = viewModel.eventFlow,
                                titleState = viewModel.noteTitle.value,
                                contentState = viewModel.noteContent.value,
                                prevNoteColor = color,
                                currentNoteColor = viewModel.noteColor.value,
                                navigateOnSaveNote = {
                                    navController.navigateUp()
                                },
                                onEvent = viewModel::onEvent
                            )
                        }

                        composable(route = Screen.ConnectedBluetoothDevicesScreen.route) {
                            val viewModel = hiltViewModel<BluetoothViewModel>()
                            val state = viewModel.state.collectAsState()
                            BluetoothDevicesScreen(
                                state = state.value,
                                onStartScan = viewModel::startScan,
                                onStopScan = viewModel::stopScan
                            )
                        }
                    }
                }
            }
        }
    }
}
