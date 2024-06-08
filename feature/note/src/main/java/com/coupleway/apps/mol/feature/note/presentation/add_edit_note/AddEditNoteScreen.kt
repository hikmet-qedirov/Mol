package com.coupleway.apps.mol.feature.note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coupleway.apps.mol.core.ui.component.NoteAlertDialog
import com.coupleway.apps.mol.core.common.R
import com.coupleway.apps.mol.feature.note.domain.model.Note
import com.coupleway.apps.mol.feature.note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

@Composable
fun AddEditNoteScreen(
    eventFlow: SharedFlow<AddEditNoteViewModel.UiEvent>,
    titleState: NoteTextFieldState,
    contentState: NoteTextFieldState,
    prevNoteColor: Int,
    currentNoteColor: Int,
    navigateOnSaveNote: () -> Unit,
    onEvent: (AddEditNoteEvent) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val alertDialogState = remember { mutableStateOf(false) }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (prevNoteColor != -1) prevNoteColor else currentNoteColor)
        )
    }

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navigateOnSaveNote()
                }

                is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    alertDialogState.value = true
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Save")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(noteBackgroundAnimatable.value)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Note.noteColors.forEach { color ->
                        val colorInt = color.toArgb()

                        Box(modifier = Modifier
                            .size(48.dp)
                            .shadow(15.dp, CircleShape)
                            .background(color)
                            .border(
                                width = 2.dp,
                                color = if (currentNoteColor == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                    onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                                }
                            }
                        )


                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = titleState.text,
                    hint = titleState.hint.ifBlank { stringResource(R.string.add_your_title) },
                    onValueChange = {
                        onEvent(AddEditNoteEvent.EnterTitle(it))
                    },
                    onFocusChange = { focusState ->
                        onEvent(AddEditNoteEvent.ChangeTitleFocus(focusState))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = contentState.text,
                    hint = contentState.hint.ifBlank { stringResource(R.string.add_your_content) },
                    onValueChange = {
                        onEvent(AddEditNoteEvent.EnterContent(it))
                    },
                    onFocusChange = { focusState ->
                        onEvent(AddEditNoteEvent.ChangeContentFocus(focusState))
                    },
                    isHintVisible = contentState.isHintVisible,
                    singleLine = false,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    )

    if (alertDialogState.value) {
        NoteAlertDialog(
            title = stringResource(R.string.warning),
            text = stringResource(R.string.do_you_want_to_save_note),
            dismissButtonText = stringResource(R.string.dismiss),
            confirmButtonText = stringResource(R.string.confirm),
            onConfirmClicked = {
                alertDialogState.value = false
                onEvent(AddEditNoteEvent.SaveNote)
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
fun PreviewAddEditNoteScreen(
    eventFlow: SharedFlow<AddEditNoteViewModel.UiEvent> = MutableStateFlow(
        AddEditNoteViewModel.UiEvent.ShowSnackBar(
            com.coupleway.apps.mol.core.ui.util.UiText.DynamicString(
                "deneme"
            )
        )
    )
) {
    com.coupleway.apps.mol.core.ui.theme.MoLTheme {
        AddEditNoteScreen(
            eventFlow = eventFlow,
            titleState = NoteTextFieldState(
                com.coupleway.apps.mol.core.ui.util.UiText.DynamicString("Title").asString(),
                com.coupleway.apps.mol.core.ui.util.UiText.DynamicString("Hint").asString(),
                false
            ),
            contentState = NoteTextFieldState(
                com.coupleway.apps.mol.core.ui.util.UiText.DynamicString("Content").asString(),
                com.coupleway.apps.mol.core.ui.util.UiText.DynamicString("Hint").asString(),
                false
            ),
            prevNoteColor = Note.noteColors[1].toArgb(),
            currentNoteColor = Note.noteColors[1].toArgb(),
            navigateOnSaveNote = {},
            onEvent = {},
        )
    }

}