package com.hussein.noteappkmm.android.note_list

import com.hussein.noteappkmm.domain.note.Note

data class NoteListState(
    val notes:List<Note> = emptyList(),
    val searchText : String = "",
    val isSearchActive:Boolean = false

)
