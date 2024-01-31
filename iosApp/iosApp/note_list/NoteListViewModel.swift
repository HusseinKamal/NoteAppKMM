//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Hussein Kamal on 31/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Foundation
import shared

extension NoteListScreen {
    @MainActor class NoteListViewModel : ObservableObject {
        private var noteDataSource : NoteDataSource? = nil
        
        private let searchNotes = SearchNote()
        private var notes = [Note]()
        @Published private(set) var filterNotes = [Note]()
        @Published var searchText = "" {
            didSet{
                self.filterNotes = searchNotes.execute(notes: self.notes ,query:searchText)
            }
        }
        @Published private(set) var isSearchActive = false
        
        init(noteDataSource : NoteDataSource? = nil){
            self.noteDataSource = noteDataSource
        }
        
        func setNoteDataSource(noteDataSource : NoteDataSource){
            self.noteDataSource = noteDataSource
        }
        
        func loadNotes(){
            noteDataSource?.getAllNotes(completionHandler : {notes , error in self.notes = notes ?? []
                self.filterNotes = self.notes
                
            })
        }
        func deleteNoteById(id : Int64?){
            if id != nil{
                noteDataSource.deleteNoteById(id : id!, completionHandler : {error in self.loadNotes
                })
            }
        }
        
        func toggleIsSearchActive(){
            isSearchActive = !.isSearchActive
            if !isSearchActive {
                searchText = ""
            }
        }
        
        func setNoteDataSource(noteDataSource:NoteDataSource){
            self.noteDataSource = noteDataSource
           /* noteDataSource.insetNote(note: Note(id: nil , title :"Note Title " ,content: "Note Content", colorHex: "0xFF2355", created : DateTimeUtil().now() ),completionHandler :{
                error in
                )})*/
        }
    }
}
