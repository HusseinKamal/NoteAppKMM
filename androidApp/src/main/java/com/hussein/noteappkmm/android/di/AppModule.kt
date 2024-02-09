package com.hussein.noteappkmm.android.di

import android.app.Application
import com.hussein.noteappkmm.data.local.DatabaseDriverFactory
import com.hussein.noteappkmm.data.note.SqlDelightNoteDataSource
import com.hussein.noteappkmm.database.NoteDatabase
import com.hussein.noteappkmm.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSqlDriver(app:Application):SqlDriver  {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Singleton
    @Provides
    fun provideNoteDataSource(driver:SqlDriver):NoteDataSource  {
        return SqlDelightNoteDataSource(NoteDatabase(driver))
    }
}