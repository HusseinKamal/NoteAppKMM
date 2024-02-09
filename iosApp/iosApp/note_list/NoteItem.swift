//
//  NoteItem.swift
//  iosApp
//
//  Created by Hussein Kamal on 31/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct NoteItem: View {
    var note:Note
    var onDeletClick: () -> Void
    var body: some View {
        VStack(alignment: .leading) {
            HStack{
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeletClick){
                    Image(systemName: "xmark").foregroundColor(.black)
                }
            }.padding(.bottom,3)//padding 3 in bottom
            Text(note.content)
                .fontWeight(.light)
                .padding(.bottom,3)
            HStack{
                Spacer()
                Text(DateTimeUtil().formatNoteDate(dateTime : note.created))
                    .font(.footnote)
                    .fontWeight(.light)
                }
        
        }.padding()
        .background(Color(hex:note.colorHex))
        .clipShape(RoundedRectangle(cornerRadius: 5.0))
    }
}

struct NoteItem_Previews: PreviewProvider {
    static var previews: some View {
        NoteItem(
            note : Note(id: nil, title: "My note", content : "Note content",colorHex : 0xFF2341 , created : DateTimeUtil().now()) , onDeletClick: {})
    }
}
