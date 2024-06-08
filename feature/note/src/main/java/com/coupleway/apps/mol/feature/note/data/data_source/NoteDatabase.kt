package com.coupleway.apps.mol.feature.note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coupleway.apps.mol.feature.note.domain.model.Note

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}