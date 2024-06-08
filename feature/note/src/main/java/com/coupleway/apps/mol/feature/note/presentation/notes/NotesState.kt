package com.coupleway.apps.mol.feature.note.presentation.notes

import com.coupleway.apps.mol.feature.note.domain.model.Note
import com.coupleway.apps.mol.feature.note.domain.util.NoteOrder
import com.coupleway.apps.mol.feature.note.domain.util.OrderType

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)