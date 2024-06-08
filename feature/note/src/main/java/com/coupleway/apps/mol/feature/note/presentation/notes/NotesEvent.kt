package com.coupleway.apps.mol.feature.note.presentation.notes

import com.coupleway.apps.mol.feature.note.domain.util.NoteOrder

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

sealed interface NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent
    data class DeleteNote(val noteId: Int) : NotesEvent
    data object RestoreNote : NotesEvent
    data object ToggleOrderSection : NotesEvent
}