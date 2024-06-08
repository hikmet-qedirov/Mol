package com.coupleway.apps.mol.feature.note.domain.use_case

import com.coupleway.apps.mol.feature.note.domain.repository.NoteRepository

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(noteId: Int) {
        repository.deleteNote(noteId)
    }
}