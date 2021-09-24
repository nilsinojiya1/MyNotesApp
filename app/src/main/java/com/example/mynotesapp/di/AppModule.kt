package com.example.mynotesapp.di

import android.app.Application
import com.example.mynotesapp.dao.NoteDAO
import com.example.mynotesapp.db.NoteDatabase
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
    fun getInstance(context: Application): NoteDatabase {
        return NoteDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun noteDao(noteDB: NoteDatabase): NoteDAO {
        return noteDB.noteDao()
    }

    @Singleton
    @Provides
    fun randomString(): String{
        return "Hello"
    }

}