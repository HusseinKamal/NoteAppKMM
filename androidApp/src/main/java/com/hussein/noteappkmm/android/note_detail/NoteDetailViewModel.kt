package com.hussein.noteappkmm.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hussein.noteappkmm.domain.note.Note
import com.hussein.noteappkmm.domain.note.NoteDataSource
import com.hussein.noteappkmm.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val stateHandle: SavedStateHandle
):ViewModel() {
    private val  noteTitle = stateHandle.getStateFlow("noteTitle","")
    private val  noteContent = stateHandle.getStateFlow("noteContent","")
    private val  isNoteTitleTextFocused = stateHandle.getStateFlow("isNoteTitleTextFocused",false)
    private val  isNoteContentTextFocused = stateHandle.getStateFlow("isNoteContentTextFocused",false)
    private val  noteColor = stateHandle.getStateFlow("noteColor",Note.generateRandomColor())//support transparent color

    val state = kotlinx.coroutines.flow.combine(noteTitle,
        isNoteTitleTextFocused,
        noteContent,
        isNoteContentTextFocused
        ,noteColor)
    {noteTitle,isNoteTitleTextFocused,noteContent,isNoteContentTextFocused,noteColor ->
        NoteDetailState(
            noteTitle = noteTitle,
            isNoteTitleHintVisible = noteTitle.isEmpty() && !isNoteTitleTextFocused,
            noteContent = noteContent,
            isNoteContentHintVisible =noteContent.isEmpty() && !isNoteContentTextFocused,
            noteColor = noteColor as Long
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState()) //We need to share this state flow between all collectors

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved= _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId : Long? = null
    init {
        stateHandle.get<Long>("noteId")?.let { existingNoteId ->
            if(existingNoteId == -1L){
                return@let
            }
            this.existingNoteId =existingNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(existingNoteId)?.let{note ->
                    stateHandle["noteTitle"] =note.title
                    stateHandle["noteContent"] =note.content
                    stateHandle["noteColor"] =note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(text:String){
        stateHandle["noteTitle"] = text
    }

    fun onNoteContentChanged(text:String){
        stateHandle["noteContent"] = text
    }

    fun onNoteTitleFocusChanged(isFocused:Boolean){
        stateHandle["isNoteTitleTextFocused"] = isFocused
    }

    fun onNoteContentFocusChanged(isFocused:Boolean){
        stateHandle["isNoteContentTextFocused"] = isFocused
    }

    fun saveNote(){
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value as Long,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }

}