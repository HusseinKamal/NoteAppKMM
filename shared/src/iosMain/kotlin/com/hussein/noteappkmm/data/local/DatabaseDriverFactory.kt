package com.hussein.noteappkmm.data.local

import com.hussein.noteappkmm.database.NoteDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(NoteDatabase.Schema,"note.db")//This should be in same level like shared Module like data/local
    }
}