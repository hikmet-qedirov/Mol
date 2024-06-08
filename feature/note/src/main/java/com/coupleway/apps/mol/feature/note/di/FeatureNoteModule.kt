package com.coupleway.apps.mol.feature.note.di

import android.app.Application
import androidx.room.Room
import com.coupleway.apps.mol.feature.note.data.data_source.NoteDatabase
import com.coupleway.apps.mol.feature.note.data.repository.NoteRepositoryImpl
import com.coupleway.apps.mol.feature.note.domain.repository.NoteRepository
import com.coupleway.apps.mol.feature.note.domain.use_case.AddNoteUseCase
import com.coupleway.apps.mol.feature.note.domain.use_case.DeleteNoteUseCase
import com.coupleway.apps.mol.feature.note.domain.use_case.GetNoteUseCase
import com.coupleway.apps.mol.feature.note.domain.use_case.GetNotesUseCase
import com.coupleway.apps.mol.feature.note.domain.use_case.NoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Hikmet Qedirov on 25.03.2024.
 */

@Module
@InstallIn(SingletonComponent::class)
object FeatureNoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repository: NoteRepository): NoteUseCase {
        return NoteUseCase(
            getNoteUseCase = GetNoteUseCase(repository),
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNoteUseCase = DeleteNoteUseCase(repository),
            addNoteUseCase = AddNoteUseCase(repository)
        )
    }

}