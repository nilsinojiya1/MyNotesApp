package com.example.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotesapp.adapters.NotesAdapter
import com.example.mynotesapp.databinding.ActivityMainBinding
import com.example.mynotesapp.db.NoteDatabase
import com.example.mynotesapp.models.Note
import com.example.mynotesapp.repositorys.NoteRepository
import com.example.mynotesapp.ui.AddNotesActivity
import com.example.mynotesapp.viewmodels.NotesViewModel
import com.example.mynotesapp.viewmodels.NotesViewModelFactory

class MainActivity : AppCompatActivity(), NotesAdapter.OnNoteListener {

    private val TAG = this::class.java.simpleName
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var notesAdapter: NotesAdapter = NotesAdapter(this)
    private lateinit var notesViewModel: NotesViewModel

    private var notes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = NoteDatabase.getInstance(application).noteDao()
        val repository = NoteRepository(dao)
        val factory = NotesViewModelFactory(repository)

        notesViewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, 1)
        binding.recyclerView.adapter = notesAdapter
        displayNotes()


        binding.fabAddNote.setOnClickListener {
            startActivity(Intent(this, AddNotesActivity::class.java))
        }

    }

    private fun displayNotes(){

        notesViewModel.notes.observe(this, Observer {
            Log.d(TAG, "displayNotes: ${it}")
            notes = it.toMutableList()
            notesAdapter.setNoteList(notes)
            notesAdapter.notifyDataSetChanged()
        })

    }

    override fun onNoteClick(position: Int) {
        var intent = Intent(this, AddNotesActivity::class.java)
        intent.putExtra("FROM", "UPDATE")
        intent.putExtra("NOTE", notes[position])
        startActivity(intent)
    }

    override fun onNoteLongClick(position: Int) {
        notesViewModel.delete(notes[position])
        notes.removeAt(position)
        notesAdapter.setNoteList(notes)
        notesAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Notes Delete", Toast.LENGTH_SHORT).show()
    }

}