package com.example.mynotesapp.viewmodels

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.models.Note
import com.example.mynotesapp.repositorys.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NoteRepository): ViewModel(), Observable {

    val notes = repository.notes

    @Bindable
    val noteTitle = MutableLiveData<String>()
    @Bindable
    val description = MutableLiveData<String>()
    @Bindable
    val screenTitle = MutableLiveData<String>()

    init {
        screenTitle.value = "Add New Note"
    }

    fun saveOrUpdate(){
        val title = noteTitle.value!!
        val noteDescription = description.value!!
        insert(Note(0, title, noteDescription, false))
        noteTitle.value = null
        description.value = null
    }

    fun insert(note: Note){
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun update(note: Note){
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun delete(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}