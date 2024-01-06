package com.hussein.noteappkmm.domain.note

interface NoteDataSource {
    suspend fun insertNote(note: Note)
    suspend fun getNoteById(id: Long) : Note?
    //suspend fun getAllNotes(note: Note) : List<Note>
    suspend fun deleteNoteById(id: Long)
    suspend fun getAllNotes(): List<Note>

}