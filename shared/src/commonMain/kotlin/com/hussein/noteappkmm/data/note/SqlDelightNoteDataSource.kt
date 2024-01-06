package com.hussein.noteappkmm.data.note

import com.hussein.noteappkmm.database.NoteDatabase
import com.hussein.noteappkmm.domain.note.Note
import com.hussein.noteappkmm.domain.note.NoteDataSource
import com.hussein.noteappkmm.domain.time.DateTimeUtil

class SqlDelightNoteDataSource(db : NoteDatabase): NoteDataSource{

    private val queries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            note.id,
            note.title,
            note.content,
            note.colorHex,
            DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }



    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id = id)
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries
            .getAllNotes()
            .executeAsList()
            .map { it.toNote() }
    }
}