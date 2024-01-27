package com.hussein.noteappkmm.android

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hussein.noteappkmm.android.note_list.HideableSearchTextField
import com.hussein.noteappkmm.android.note_list.NoteItem
import com.hussein.noteappkmm.android.note_list.NoteListViewModel
import com.hussein.noteappkmm.domain.note.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    viewModel: NoteListViewModel = hiltViewModel(),
){
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = true){
        viewModel.loadNotes()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                backgroundColor = Color.Black
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                    tint = Color.White
                )
            }
        }
    ){ paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                HideableSearchTextField(
                    text = state.searchText,
                    isSearchActive = state.isSearchActive,
                    onTextChange = viewModel::onSearchTextChange,
                    onSearchClick = viewModel::onToggleSearch,
                    onSearchClose = viewModel::onToggleSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(90.dp)
                )
                this@Column.AnimatedVisibility( //This for refer to specific column that contains this composabel
                    visible = !state.isSearchActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(text = "All Notes", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                }
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.notes , key = {it.id!!}){note: Note -> //key for refer to specific one in make animateItemPlacement()
                    NoteItem(
                        note = note,
                        backColor = Color(note.colorHex),
                        onNoteClick = {

                        },
                        onNoteDelete = {
                            viewModel.deleteNoteById(note.id!!)
                        },
                        modifier = Modifier.fillMaxWidth().padding(16.dp).animateItemPlacement()

                    )
                }
            }
        }
    }
}