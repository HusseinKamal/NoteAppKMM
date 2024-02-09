//
//  NoteDetailScreen.swift
//  iosApp
//
//  Created by Hussein Kamal on 31/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteDetailScreen: View {
    
    private var noteDateSource : NoteDataSource?
    private var noteId: Int64? = nil
    
    @StateObject var viewModel = NoteDetailViewModel(noteDataSource: nil)

    @Environment(\.presentationMode) var presentation

    init(noteDataSource : NoteDataSource?= nil ,  noteId: Int64? = nil){
        self.noteDataSource = noteDataSource
        slef.noteId = noteId
    }
    
    var body: some View {
         VStack(alignment: .leading) {
             TextField("Enter a title...", text: $viewModel.noteTitle)
                 .font(.title)
             TextField("Enter some content...", text: $viewModel.noteContent)
             Spacer()
         }.toolbar(content: {
             Button(action: {
                 viewModel.saveNote {
                     self.presentation.wrappedValue.dismiss()
                 }
             }) {
                 Image(systemName: "checkmark")
             }
         })
         .padding()
         .background(Color(hex: viewModel.noteColor))
         .onAppear {
             viewModel.setParamsAndLoadNote(noteDataSource: noteDataSource, noteId: noteId)
         }
     }
 }

 struct NoteDetailScreen_Previews: PreviewProvider {
     static var previews: some View {
         EmptyView()
     }
 }