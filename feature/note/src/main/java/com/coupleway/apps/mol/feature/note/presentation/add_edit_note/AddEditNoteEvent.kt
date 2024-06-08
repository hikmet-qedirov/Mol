package com.coupleway.apps.mol.feature.note.presentation.add_edit_note

import androidx.annotation.ColorInt
import androidx.compose.ui.focus.FocusState

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
sealed interface AddEditNoteEvent {
    data class EnterTitle(val value: String) : AddEditNoteEvent
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent
    data class EnterContent(val value: String) : AddEditNoteEvent
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent
    data class ChangeColor(@ColorInt val color: Int) : AddEditNoteEvent
    data object SaveNote : AddEditNoteEvent
}