//
//  HideableSearchTextField.swift
//  iosApp
//
//  Created by Hussein Kamal on 31/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct HideableSearchTextField<Destination: View>: View {
    var onSearchToggle : () -> Void
    var destinationProvider: () -> Destination
    var isSearchActive : Bool
    @Binding var searchText:String
    
    var body: some View {
        HStack {
            TextField("Search...",text: $searchText)
                .textFieldStyle(.roundedBorder)
                .opacity(isSearchActive ? 1 : 0)
            if !isSearchActive {
                Spacer()
            }
            Button(action: onSearchToggle){
                Image(systemName: isSearchActive ? "xmark" : "magnifyingglass")
                    .foregroundColor(.black)
            }
            NavigationLink(destination: destinationProvider()){
                Image(systemName: "plus")
                    .foregroundColor(.black)
            }
        }
    }
}

struct HideableSearchTextField_Previews: PreviewProvider {
    static var previews: some View {
        HideableSearchTextField(
            onSearchToggle: {
                
            },
            destinationProvider: { EmptyView() },
            isSearchActive: true,
            searchText:.constant(("Hello World")))
    }
}
