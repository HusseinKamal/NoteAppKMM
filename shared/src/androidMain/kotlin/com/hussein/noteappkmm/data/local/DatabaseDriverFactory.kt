package com.hussein.noteappkmm.data.local

import android.content.Context
import com.hussein.noteappkmm.database.NoteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(NoteDatabase.Schema, context, "note.db")//This should be in same level like shared Module like data/local
    }
}