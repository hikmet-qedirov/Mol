package com.coupleway.apps.mol.feature.note.domain.use_case

import com.coupleway.apps.mol.feature.note.domain.model.InvalidNoteException
import com.coupleway.apps.mol.feature.note.domain.model.Note
import com.coupleway.apps.mol.feature.note.domain.repository.NoteRepository

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
class AddNoteUseCase(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title can not be empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content can not be empty")
        }
        repository.insertNote(note)
    }
}