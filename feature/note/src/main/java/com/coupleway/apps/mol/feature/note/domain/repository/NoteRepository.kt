package com.coupleway.apps.mol.feature.note.domain.repository

import com.coupleway.apps.mol.feature.note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(noteId: Int)
}