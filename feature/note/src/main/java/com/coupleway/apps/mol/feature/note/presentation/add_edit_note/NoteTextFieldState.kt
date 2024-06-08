package com.coupleway.apps.mol.feature.note.presentation.add_edit_note

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)