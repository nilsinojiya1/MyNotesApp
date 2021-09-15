package com.example.mynotesapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mynotesapp.MainActivity
import com.example.mynotesapp.databinding.ActivityAddNotesBinding
import com.example.mynotesapp.db.NoteDatabase
import com.example.mynotesapp.models.Note
import com.example.mynotesapp.repositorys.NoteRepository
import com.example.mynotesapp.viewmodels.NotesViewModel
import com.example.mynotesapp.viewmodels.NotesViewModelFactory

class AddNotesActivity : AppCompatActivity() {

    private var _binding: ActivityAddNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesViewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = NoteDatabase.getInstance(application).noteDao()
        val repository = NoteRepository(dao)
        val factory = NotesViewModelFactory(repository)

        notesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
        binding.viewModel = notesViewModel
        binding.lifecycleOwner = this
        var note: Note
        var intent = intent
        var id:Int = 0
        var isSync = false
        if(intent.getStringExtra("FROM").equals("UPDATE")){
            note = intent.getParcelableExtra<Note>("NOTE")!!
            id = note.id
            binding.tvTitle.text = "Update Note"
            notesViewModel.noteTitle.value = note.title.toString()
            notesViewModel.description.value = note.description.toString()
        }




        binding.fabSave.setOnClickListener {
            if(intent.getStringExtra("FROM").equals("UPDATE")){
                notesViewModel.update(Note(id, binding.etTitle.text.toString(), binding.etDescription.text.toString(), isSync))
            } else {
                notesViewModel.saveOrUpdate()
            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}