package com.example.mynotesapp.repositorys

import com.example.mynotesapp.dao.NoteDAO
import com.example.mynotesapp.models.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val dao: NoteDAO) {

    val notes = dao.getAllNotes()

    suspend fun insertNote(note: Note){
        dao.insertNote(note)
    }

    suspend fun updateNote(note: Note){
        dao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        dao.deleteNote(note)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }

}