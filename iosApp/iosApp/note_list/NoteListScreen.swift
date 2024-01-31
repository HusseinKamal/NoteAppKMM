//
//  NoteListScreen.swift
//  iosApp
//
//  Created by Hussein Kamal on 31/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteListScreen: View {
    private var noteDataSource : NoteDataSource
    @StateObject var viewmodel = NoteListViewModel(noteDataSource: nil)
    @State private var isNoteSelected = false
    @State private var selectedNoteId : Int64? = nil
    
    init(noteDataSource : NoteDataSource){
        self.noteDataSource = noteDataSource
    }
    var body: some View {
        VStack{
            ZStack {
                NavigationLink(destination:NoteDetailScreen(noteDataSource: self.noteDataSource, noteId: selectedNoteId), isActive: $isNoteSelected){
                    EmptyView()
                }.hidden()
                
                HideableSearchTextField<NoteDetailScreen>(onSearchToggle: {
                    viewmodel.toggleIsSearchActive()
                }, destinationProvider:{
                   NoteDetailScreen(noteDataSource: noteDataSource, noteId: selectedNoteId)
                }, isSearchActive: viewmodel.isSearchActive
             , searchText: $viewmodel.searchText)
                .frame(maxWidth : .infinity ,minHeight : 40)
                .padding()
                
                if !viewmodel.isSearchActive {
                    Text("All Note")
                        .font(.title2)
                }
            }
            List {
                ForEach(viewmodel.filterNotes,id : \.self.id){ note in
                    Button(action:{
                        isNoteSelected = true
                        selectedNoteId = note.id?.int64Value
                    })
                    {
                        NoteItem(note: note, onDeletClick: {
                            viewmodel.deleteNoteById(id: note.id?.int64Value)
                        })
                    }
                }
            }.onAppear {
                viewmodel.loadNotes()
            }
            .listStyle(.plain)
            .listRowSeparator(.hidden)
        }.onAppear{
            viewmodel.setNoteDataSource(noteDataSource: noteDataSource)
        }
    }
}

struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
       EmptyView()
    }
}
