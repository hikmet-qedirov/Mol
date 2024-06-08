package com.coupleway.apps.mol.feature.note.domain.use_case

import com.coupleway.apps.mol.feature.note.domain.model.Note
import com.coupleway.apps.mol.feature.note.domain.repository.NoteRepository

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
class GetNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}