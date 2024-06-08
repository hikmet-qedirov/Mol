package com.coupleway.apps.mol.feature.note.presentation.notes

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coupleway.apps.mol.core.ui.component.NoteAlertDialog
import com.coupleway.apps.mol.core.ui.util.Screen
import com.coupleway.apps.mol.core.common.R
import com.coupleway.apps.mol.feature.note.domain.model.Note
import com.coupleway.apps.mol.feature.note.domain.util.NoteOrder
import com.coupleway.apps.mol.feature.note.domain.util.OrderType
import com.coupleway.apps.mol.feature.note.presentation.notes.components.NoteItem
import com.coupleway.apps.mol.feature.note.presentation.notes.components.OrderSection
import kotlinx.coroutines.launch

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

@Composable
fun NotesScreen(
    state: NotesState,
    onEvent: (NotesEvent) -> Unit,
    navigateTo: (String) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val alertDialogState = remember { mutableStateOf(false) }
    val deletedNoteId = remember<MutableState<Int?>> { mutableStateOf(null) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateTo(Screen.AddEditNoteScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_note)
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.your_note),
                        style = MaterialTheme.typography.displaySmall
                    )

                    if (state.notes.size > 1) {
                        IconButton(onClick = {
                            onEvent(NotesEvent.ToggleOrderSection)
                        }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.sort)
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        noteOrder = state.noteOrder,
                        onOrderChange = { noteOrder ->
                            onEvent(NotesEvent.Order(noteOrder))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.notes) { note ->
                        NoteItem(
                            note = note,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigateTo(Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}") // Todo: need to change
                                },
                            onDeleteClick = {
                                alertDialogState.value = true
                                deletedNoteId.value = note.id
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    )

    if (alertDialogState.value) {
        NoteAlertDialog(
            title = stringResource(R.string.warning),
            text = stringResource(R.string.do_you_want_to_delete_note),
            dismissButtonText = stringResource(R.string.dismiss),
            confirmButtonText = stringResource(R.string.confirm),
            onConfirmClicked = {
                val noteId = deletedNoteId.value ?: return@NoteAlertDialog
                Log.d("test_ucun", "delete note id ::${noteId}")

                alertDialogState.value = false
                onEvent(NotesEvent.DeleteNote(noteId))
                scope.launch {
                    val result = snackBarHostState.showSnackbar(
                        message = context.getString(R.string.note_deleted),
                        actionLabel = context.getString(R.string.undo),
                        duration = SnackbarDuration.Short
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        onEvent(NotesEvent.RestoreNote)
                    }
                }
            },
            onDismissRequest = {
                alertDialogState.value = false
            }
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewNotesScreen() {
    com.coupleway.apps.mol.core.ui.theme.MoLTheme {
        NotesScreen(
            NotesState(
                listOf(
                    Note(
                        id = 12,
                        "Couple Way",
                        "This is my first Note",
                        11L,
                        Note.noteColors.random().toArgb()
                    ),
                    Note(
                        id = 1223,
                        "Couple Way second",
                        "This is my first Note",
                        12L,
                        Note.noteColors.random().toArgb()
                    ),
                    Note(
                        id = 12334,
                        "Couple Way third",
                        "This is my first Note",
                        13L,
                        Note.noteColors.random().toArgb()
                    )
                ),
                noteOrder = NoteOrder.Date(OrderType.Descending)
            ),
            onEvent = {},
            navigateTo = {}
        )
    }
}