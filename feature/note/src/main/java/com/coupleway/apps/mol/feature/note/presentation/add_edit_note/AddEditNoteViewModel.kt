package com.coupleway.apps.mol.feature.note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coupleway.apps.mol.core.ui.util.UiText
import com.coupleway.apps.mol.core.common.R
import com.coupleway.apps.mol.feature.note.domain.model.InvalidNoteException
import com.coupleway.apps.mol.feature.note.domain.model.Note
import com.coupleway.apps.mol.feature.note.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState()
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState()
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId == -1) return@let
            viewModelScope.launch {
                noteUseCase.getNoteUseCase(noteId)?.also { note ->
                    currentNoteId = note.id
                    _noteTitle.value = _noteContent.value.copy(
                        text = note.title,
                        isHintVisible = false
                    )
                    _noteContent.value = _noteContent.value.copy(
                        text = note.content,
                        isHintVisible = false
                    )
                    _noteColor.intValue = note.color
                }
            }
        }

    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.intValue = event.color
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = event.focusState.isFocused.not() && _noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = event.focusState.isFocused.not() && _noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnterContent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.EnterTitle -> {
                _noteTitle.value = _noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCase.addNoteUseCase(
                            Note(
                                id = currentNoteId,
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                color = noteColor.value,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {

                        val errorMessage = e.message?.let { exceptionMessage ->
                            UiText.DynamicString(exceptionMessage)
                        } ?: UiText.StringResource(R.string.error_occurred)

                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = errorMessage
                            )
                        )
                    }
                }
            }
        }
    }

    sealed interface UiEvent {
        data class ShowSnackBar(val message: UiText) : UiEvent
        data object SaveNote : UiEvent
    }
}