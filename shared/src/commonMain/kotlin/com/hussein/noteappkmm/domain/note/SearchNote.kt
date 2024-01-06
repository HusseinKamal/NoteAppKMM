package com.hussein.noteappkmm.domain.note

import com.hussein.noteappkmm.domain.time.DateTimeUtil

class SearchNote {
    fun execute(notes : List<Note> , query :String) : List<Note>{
        if(query.isEmpty()){
            return notes
        }
        return notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
                    it.content.trim().lowercase().contains(query.lowercase())
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.created)
        }
    }
}