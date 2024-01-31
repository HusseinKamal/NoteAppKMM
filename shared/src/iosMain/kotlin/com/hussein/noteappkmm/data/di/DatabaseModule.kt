package com.hussein.noteappkmm.data.di

import com.hussein.noteappkmm.data.local.DatabaseDriverFactory
import com.hussein.noteappkmm.data.note.SqlDelightNoteDataSource
import com.hussein.noteappkmm.database.NoteDatabase
import com.hussein.noteappkmm.domain.note.NoteDataSource

class DatabaseModule {
    private val factory by lazy { DatabaseDriverFactory()}
    val noteDataSource : NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}