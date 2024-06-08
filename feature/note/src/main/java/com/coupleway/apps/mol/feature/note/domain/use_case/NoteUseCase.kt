package com.coupleway.apps.mol.feature.note.domain.use_case

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
data class NoteUseCase(
    val getNoteUseCase: GetNoteUseCase,
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
)
