package com.example.mynotesapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotesapp.models.Note

@Dao
interface NoteDAO {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>
}