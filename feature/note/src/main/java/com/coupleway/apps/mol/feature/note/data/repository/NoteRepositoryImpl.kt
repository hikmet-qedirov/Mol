package com.coupleway.apps.mol.feature.note.data.repository

import android.util.Log
import com.coupleway.apps.mol.feature.note.data.data_source.NoteDao
import com.coupleway.apps.mol.feature.note.domain.model.Note
import com.coupleway.apps.mol.feature.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */
class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(noteId: Int) {
        val note = dao.getNoteById(noteId) ?: return
        Log.d("test_ucun", "repo deleteNote ::${note.id}")
        return dao.deleteNote(note)
    }
}