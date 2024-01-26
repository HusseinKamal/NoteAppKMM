package com.hussein.noteappkmm.android.note_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.noteappkmm.domain.note.Note
import com.hussein.noteappkmm.domain.note.NoteDataSource
import com.hussein.noteappkmm.domain.note.SearchNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle // restore our state for view model
) : ViewModel() {
    private val searchNotes = SearchNote()
    private val notes =  savedStateHandle.getStateFlow("notes", emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive",false)

    val state = combine(notes,searchText,isSearchActive){ notes, searchText, isSearchActive ->
        NoteListState(
            notes = searchNotes.execute(
            notes = notes,
            query = searchText
        ), searchText = searchText,isSearchActive = isSearchActive)
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),NoteListState())

    fun loadNotes(){
        viewModelScope.launch {
            savedStateHandle["notes"] = noteDataSource.getAllNotes()
        }
    }
    fun onSearchTextChange(text:String){
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch(text:String){
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if(!isSearchActive.value){
            savedStateHandle["searchText"] = ""

        }
    }
    fun deleteNoteById(id:Long){
        viewModelScope.launch {
            noteDataSource.deleteNoteById(id)
            loadNotes()
        }
    }
}